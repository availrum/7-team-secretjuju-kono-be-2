package org.secretjuju.kono.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.secretjuju.kono.dto.request.CoinRequestDto;
import org.secretjuju.kono.dto.request.CoinSellBuyRequestDto;
import org.secretjuju.kono.dto.response.CoinResponseDto;
import org.secretjuju.kono.entity.CashBalance;
import org.secretjuju.kono.entity.CoinHolding;
import org.secretjuju.kono.entity.CoinInfo;
import org.secretjuju.kono.entity.CoinTransaction;
import org.secretjuju.kono.entity.User;
import org.secretjuju.kono.repository.CoinRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CoinService {

	private final CoinRepository coinRepository;
	private final UserService userService;

	public CoinService(CoinRepository coinRepository, UserService userService) {
		this.coinRepository = coinRepository;
		this.userService = userService;
	}

	public CoinResponseDto getCoinByName(CoinRequestDto coinRequestDto) {
		Optional<CoinInfo> coinInfo = coinRepository.findByTicker(coinRequestDto.getTicker());
		CoinResponseDto coinResponseDto = new CoinResponseDto(coinInfo.orElse(null));
		return coinResponseDto;
	}

	@Transactional
	public void createCoinOrder(CoinSellBuyRequestDto coinSellBuyRequestDto) {
		// 현재 로그인한 사용자 정보를 가져옵니다.
		User currentUser = userService.getCurrentUser();

		// 해당 코인 정보를 가져옵니다.
		Optional<CoinInfo> coinInfoOpt = coinRepository.findByTicker(coinSellBuyRequestDto.getTicker());
		if (coinInfoOpt.isEmpty()) {
			throw new RuntimeException("해당 코인을 찾을 수 없습니다.");
		}
		CoinInfo coinInfo = coinInfoOpt.get();

		// 거래 내역을 기록합니다.
		CoinTransaction transaction = new CoinTransaction();
		transaction.setUser(currentUser);
		transaction.setCoinInfo(coinInfo);
		transaction.setOrderType(coinSellBuyRequestDto.getOrderType());
		transaction.setOrderQuantity(coinSellBuyRequestDto.getOrderQuantity());
		transaction.setOrderPrice(coinSellBuyRequestDto.getOrderPrice());
		transaction.setOrderAmount(coinSellBuyRequestDto.getOrderAmount());
		transaction.setCreatedAt(LocalDateTime.now());
		currentUser.addTransaction(transaction);

		// 거래 타입에 따라 코인 보유량과 현금 잔액을 업데이트합니다.
		if ("buy".equalsIgnoreCase(coinSellBuyRequestDto.getOrderType())) {
			// 구매 시 처리
			processBuy(currentUser, coinInfo, coinSellBuyRequestDto);
		} else if ("sell".equalsIgnoreCase(coinSellBuyRequestDto.getOrderType())) {
			// 판매 시 처리
			processSell(currentUser, coinInfo, coinSellBuyRequestDto);
		} else {
			throw new RuntimeException("유효하지 않은 거래 타입입니다.");
		}
	}

	private void processBuy(User user, CoinInfo coinInfo, CoinSellBuyRequestDto request) {
		// 현금 잔액 확인
		CashBalance cashBalance = user.getCashBalance();
		if (cashBalance == null) {
			cashBalance = new CashBalance();
			user.setCashBalance(cashBalance);
		}

		// 현금 잔액이 충분한지 확인
		if (cashBalance.getBalance() < request.getOrderAmount()) {
			throw new RuntimeException("현금 잔액이 부족합니다.");
		}

		// 현금 잔액 감소
		cashBalance.setBalance(cashBalance.getBalance() - request.getOrderAmount());

		// 코인 보유량 업데이트
		Optional<CoinHolding> existingHolding = user.getCoinHoldings().stream()
				.filter(holding -> holding.getCoinInfo().getId().equals(coinInfo.getId())).findFirst();

		if (existingHolding.isPresent()) {
			// 기존 보유량이 있는 경우 수량 증가
			CoinHolding holding = existingHolding.get();
			holding.setHoldingQuantity(holding.getHoldingQuantity() + request.getOrderQuantity());
			holding.setHoldingPrice(request.getOrderPrice());
		} else {
			// 기존 보유량이 없는 경우 새로 생성
			CoinHolding newHolding = new CoinHolding();
			newHolding.setCoinInfo(coinInfo);
			newHolding.setHoldingQuantity(request.getOrderQuantity());
			newHolding.setHoldingPrice(request.getOrderPrice());
			user.addCoinHolding(newHolding);
		}
	}

	private void processSell(User user, CoinInfo coinInfo, CoinSellBuyRequestDto request) {
		// 해당 코인 보유량 확인
		Optional<CoinHolding> existingHolding = user.getCoinHoldings().stream()
				.filter(holding -> holding.getCoinInfo().getId().equals(coinInfo.getId())).findFirst();

		if (existingHolding.isEmpty() || existingHolding.get().getHoldingQuantity() < request.getOrderQuantity()) {
			throw new RuntimeException("코인 보유량이 부족합니다.");
		}

		// 코인 보유량 감소
		CoinHolding holding = existingHolding.get();
		holding.setHoldingQuantity(holding.getHoldingQuantity() - request.getOrderQuantity());

		// 코인을 모두 판매한 경우 보유 목록에서 제거
		if (holding.getHoldingQuantity() <= 0) {
			user.getCoinHoldings().remove(holding);
		}

		// 현금 잔액 증가
		CashBalance cashBalance = user.getCashBalance();
		if (cashBalance == null) {
			cashBalance = new CashBalance();
			user.setCashBalance(cashBalance);
		}
		cashBalance.setBalance(cashBalance.getBalance() + request.getOrderAmount());
	}
}
