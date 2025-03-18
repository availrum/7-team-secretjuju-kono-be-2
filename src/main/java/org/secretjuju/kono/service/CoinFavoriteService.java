package org.secretjuju.kono.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.secretjuju.kono.entity.CoinFavorite;
import org.secretjuju.kono.entity.CoinInfo;
import org.secretjuju.kono.entity.User;
import org.secretjuju.kono.repository.CoinFavoriteRepository;
import org.secretjuju.kono.repository.CoinInfoRepository;
import org.secretjuju.kono.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoinFavoriteService {
	private final UserRepository userRepository;
	private final CoinFavoriteRepository coinFavoriteRepository;
	private final CoinInfoRepository coinInfoRepository;

	public CoinFavoriteService(UserRepository userRepository, CoinFavoriteRepository coinFavoriteRepository, CoinInfoRepository coinInfoRepository) {
		this.userRepository = userRepository;
		this.coinFavoriteRepository = coinFavoriteRepository;
		this.coinInfoRepository = coinInfoRepository;
	}

	@Transactional(readOnly = true)
	public List<CoinInfo> findFavoriteCoinsByUserId(Integer userId) {
		// 사용자 존재 여부 확인
		userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		
		// 사용자의 모든 관심 코인 조회
		List<CoinFavorite> favorites = coinFavoriteRepository.findAllByUserId(userId);
		
		if (favorites.isEmpty()) {
			return new ArrayList<>();
		}
		
		// 관심 코인 정보 조회
		return favorites.stream()
				.map(CoinFavorite::getCoinInfo)
				.collect(Collectors.toList());
	}
	
	@Transactional
	public void addFavoriteCoin(Integer userId, String ticker) {
		// 사용자 존재 여부 확인
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		
		// 코인 존재 여부 확인
		CoinInfo coinInfo = coinInfoRepository.findByTicker(ticker)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코인입니다."));
		
		// 이미 등록된 관심 코인인지 확인
		boolean exists = coinFavoriteRepository.existsByUserIdAndCoinInfoId(userId, coinInfo.getId());
		if (exists) {
			throw new IllegalStateException("이미 관심 코인으로 등록되어 있습니다.");
		}
		
		// 관심 코인 등록
		CoinFavorite coinFavorite = new CoinFavorite();
		coinFavorite.setUser(user);
		coinFavorite.setCoinInfo(coinInfo);
		
		coinFavoriteRepository.save(coinFavorite);
	}
	
	@Transactional
	public void deleteFavoriteCoin(Integer userId, String ticker) {
		// 사용자 존재 여부 확인
		userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
		
		// 코인 존재 여부 확인
		CoinInfo coinInfo = coinInfoRepository.findByTicker(ticker)
				.orElseThrow(() -> new IllegalArgumentException("존재하지 않는 코인입니다."));
		
		// 관심 코인 삭제
		coinFavoriteRepository.deleteByUserIdAndCoinInfoId(userId, coinInfo.getId());
	}
}
