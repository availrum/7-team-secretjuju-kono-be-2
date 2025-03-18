package org.secretjuju.kono.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.secretjuju.kono.dto.response.CashBalanceResponseDto;
import org.secretjuju.kono.dto.response.CoinHoldingDetailResponseDto;
import org.secretjuju.kono.dto.response.CoinHoldingResponseDto;
import org.secretjuju.kono.dto.response.TransactionHistoryResponseDto;
import org.secretjuju.kono.entity.CoinHolding;
import org.secretjuju.kono.entity.CoinInfo;
import org.secretjuju.kono.entity.CoinTransaction;
import org.secretjuju.kono.entity.User;
import org.secretjuju.kono.repository.CoinRepository;
import org.secretjuju.kono.repository.CoinTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

	private final CoinTransactionRepository coinTransactionRepository;
	private final CoinRepository coinInfoRepository;
	private final UserService userService;

	@Transactional(readOnly = true)
	public List<TransactionHistoryResponseDto> getTransactionHistory() {
		User currentUser = userService.getCurrentUser();
		List<CoinTransaction> transactions = coinTransactionRepository.findByUserOrderByCreatedAtDesc(currentUser);

		return transactions.stream().map(this::convertToTransactionsResponse).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CashBalanceResponseDto getCashBalance() {
		User currentUser = userService.getCurrentUser();
		return new CashBalanceResponseDto(currentUser.getCashBalance().getBalance());
	}

	@Transactional(readOnly = true)
	public List<CoinHoldingResponseDto> getCoinHoldings() {
		User currentUser = userService.getCurrentUser();
		return currentUser.getCoinHoldings().stream().map(this::convertToCoinHoldingResponse)
				.collect(Collectors.toList());
	}

	// 특정코인 보유여부와 보유하고있는 개수를 조회
	@Transactional(readOnly = true)
	public CoinHoldingDetailResponseDto getCoinHoldingDetail(String ticker) {
		User currentUser = userService.getCurrentUser();
		Optional<CoinInfo> coinInfo = coinInfoRepository.findByTicker(ticker);

		if (coinInfo.isEmpty()) {
			return new CoinHoldingDetailResponseDto(false, 0);
		}

		double totalQuantity = currentUser.getCoinHoldings().stream()
				.filter(h -> h.getCoinInfo().getId().equals(coinInfo.get().getId()))
				.mapToDouble(CoinHolding::getHoldingQuantity).sum();

		if (totalQuantity == 0) {
			return new CoinHoldingDetailResponseDto(false, 0);
		}

		return new CoinHoldingDetailResponseDto(true, totalQuantity);
	}

	private TransactionHistoryResponseDto convertToTransactionsResponse(CoinTransaction transaction) {
		return new TransactionHistoryResponseDto(transaction.getId(), transaction.getUser().getId(),
				transaction.getCoinInfo().getId(), transaction.getCoinInfo().getKrCoinName(),
				transaction.getCoinInfo().getTicker(), transaction.getOrderType(), transaction.getOrderQuantity(),
				transaction.getOrderPrice(), transaction.getOrderAmount(), transaction.getCreatedAt());
	}

	private CoinHoldingResponseDto convertToCoinHoldingResponse(CoinHolding holding) {
		return new CoinHoldingResponseDto(holding.getCoinInfo().getTicker(), holding.getCoinInfo().getKrCoinName(),
				holding.getHoldingQuantity(), holding.getHoldingPrice());
	}
}
