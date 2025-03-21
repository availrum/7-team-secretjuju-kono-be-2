package org.secretjuju.kono.service;

import java.util.List;
import java.util.stream.Collectors;

import org.secretjuju.kono.dto.response.ApiResponseDto;
import org.secretjuju.kono.dto.response.DailyRankingResponseDto;
import org.secretjuju.kono.dto.response.TotalRankingResponseDto;
import org.secretjuju.kono.entity.Badge;
import org.secretjuju.kono.entity.DailyRanking;
import org.secretjuju.kono.entity.TotalRanking;
import org.secretjuju.kono.entity.User;
import org.secretjuju.kono.repository.CashBalanceRepository;
import org.secretjuju.kono.repository.CoinHoldingRepository;
import org.secretjuju.kono.repository.DailyRankingRepository;
import org.secretjuju.kono.repository.TotalRankingRepository;
import org.secretjuju.kono.repository.UserRepository;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RankingService {
	private final UserRepository userRepository;
	private final TotalRankingRepository totalRankingRepository;
	private final DailyRankingRepository dailyRankingRepository;
	private final CoinHoldingRepository coinHoldingRepository;
	private final CashBalanceRepository cashBalanceRepository;

	// 코인의 현재 가격을 가져오는 함수 (가정)
	private Double getCurrentCoinPrice(String ticker) {
		// TODO: Upbit API 연동
		return 0.0;
	}

	// 사용자의 총 자산 계산
	private Long calculateTotalAssets(User user) {
		// 현금 잔액
		Long cashBalance = user.getCashBalance().getBalance();

		// 코인 자산
		Long coinAssets = coinHoldingRepository.findByUser(user).stream().map(holding -> {
			Double currentPrice = getCurrentCoinPrice(holding.getCoinInfo().getTicker());
			return (long) (holding.getHoldingQuantity() * currentPrice);
		}).reduce(0L, Long::sum);

		return cashBalance + coinAssets;
	}

	// 수익률 계산
	private Double calculateProfitRate(Long currentAssets, Long lastDayAssets) {
		if (lastDayAssets == 0)
			return 0.0;
		return ((double) (currentAssets - lastDayAssets) / lastDayAssets) * 100;
	}

	@Transactional
	public void updateRankings() {
		log.info("랭킹 업데이트 시작");

		// 1. 모든 사용자 조회
		List<User> users = userRepository.findAll();

		// 2. 각 사용자의 총 자산 계산 및 랭킹 업데이트
		for (User user : users) {
			Long currentAssets = calculateTotalAssets(user);

			// TotalRanking 업데이트
			TotalRanking totalRanking = totalRankingRepository.findByUser(user).orElse(new TotalRanking(user));
			totalRanking.setCurrentTotalAssets(currentAssets);
			totalRanking.updateTime();
			totalRankingRepository.save(totalRanking);

			// DailyRanking 업데이트
			DailyRanking dailyRanking = dailyRankingRepository.findByUser(user).orElse(new DailyRanking(user));
			dailyRanking.setCurrentTotalAssets(currentAssets);
			dailyRanking.setProfitRate(calculateProfitRate(currentAssets, dailyRanking.getLastDayTotalAssets()));
			dailyRanking.updateTime();
			dailyRankingRepository.save(dailyRanking);
		}

		// 3. 전체 랭킹 순위 업데이트
		List<TotalRanking> totalRankings = totalRankingRepository.findAllByOrderByCurrentTotalAssetsDesc();
		for (int i = 0; i < totalRankings.size(); i++) {
			totalRankings.get(i).setTotalRank(i + 1);
			totalRankings.get(i).updateTime();
		}
		totalRankingRepository.saveAll(totalRankings);

		// 4. 일간 랭킹 순위 업데이트
		List<DailyRanking> dailyRankings = dailyRankingRepository.findAllByOrderByProfitRateDesc();
		for (int i = 0; i < dailyRankings.size(); i++) {
			dailyRankings.get(i).setDailyRank(i + 1);
			dailyRankings.get(i).updateTime();
		}
		dailyRankingRepository.saveAll(dailyRankings);

		log.info("랭킹 업데이트 완료");
	}

	@Transactional
	public void updateLastDayAssets() {
		log.info("어제 총 자산 업데이트 시작");

		List<DailyRanking> dailyRankings = dailyRankingRepository.findAll();
		for (DailyRanking ranking : dailyRankings) {
			ranking.setLastDayTotalAssets(ranking.getCurrentTotalAssets());
			ranking.updateTime();
		}
		dailyRankingRepository.saveAll(dailyRankings);

		log.info("어제 총 자산 업데이트 완료");
	}

	// 일간 랭킹 조회
	public ApiResponseDto<DailyRankingResponseDto> getDailyRanking() {
		// 일간 랭킹 상위 100개 조회
		List<DailyRanking> dailyRankings = dailyRankingRepository.findTop100ByOrderByDailyRankAsc();

		List<DailyRankingResponseDto.RankingItemDto> rankingItems = dailyRankings.stream().map(ranking -> {
			User user = ranking.getUser();

			// 뱃지 이미지 URL 리스트 가져오기
			List<String> badgeImageUrls = user.getBadges().stream().map(Badge::getBadgeImageUrl)
					.collect(Collectors.toList());

			return DailyRankingResponseDto.RankingItemDto.builder().nickname(user.getNickname())
					.profileImageUrl(user.getProfileImageUrl()).badgeImageUrl(badgeImageUrls)
					.profitRate(ranking.getProfitRate()).rank(ranking.getDailyRank()).build();
		}).collect(Collectors.toList());

		DailyRankingResponseDto responseDto = DailyRankingResponseDto.builder().data(rankingItems).build();

		return new ApiResponseDto<>("Daily ranking retrieved", responseDto);
	}

	// 전체 랭킹 조회
	public ApiResponseDto<TotalRankingResponseDto> getTotalRanking() {
		// 전체 랭킹 상위 100개 조회
		List<TotalRanking> totalRankings = totalRankingRepository.findTop100ByOrderByTotalRankAsc();

		List<TotalRankingResponseDto.RankingItemDto> rankingItems = totalRankings.stream().map(ranking -> {
			User user = ranking.getUser();

			// 뱃지 이미지 URL 리스트 가져오기
			List<String> badgeImageUrls = user.getBadges().stream().map(Badge::getBadgeImageUrl)
					.collect(Collectors.toList());

			return TotalRankingResponseDto.RankingItemDto.builder().nickname(user.getNickname())
					.profileImageUrl(user.getProfileImageUrl()).badgeImageUrl(badgeImageUrls)
					.totalAssets(ranking.getCurrentTotalAssets()).rank(ranking.getTotalRank()).build();
		}).collect(Collectors.toList());

		TotalRankingResponseDto responseDto = TotalRankingResponseDto.builder().data(rankingItems).build();

		return new ApiResponseDto<>("Total ranking retrieved", responseDto);
	}
}