package org.secretjuju.kono.service;

import java.util.List;
import java.util.stream.Collectors;

import org.secretjuju.kono.dto.response.CashBalanceResponseDto;
import org.secretjuju.kono.dto.response.TransactionHistoryResponseDto;
import org.secretjuju.kono.entity.CoinTransaction;
import org.secretjuju.kono.entity.User;
import org.secretjuju.kono.repository.CoinTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

	private final CoinTransactionRepository coinTransactionRepository;
	private final UserService userService;

	@Transactional(readOnly = true)
	public List<TransactionHistoryResponseDto> getTransactionHistory() {
		User currentUser = userService.getCurrentUser();
		List<CoinTransaction> transactions = coinTransactionRepository.findByUserOrderByCreatedAtDesc(currentUser);

		return transactions.stream().map(this::convertToResponse).collect(Collectors.toList());
	}

	@Transactional(readOnly = true)
	public CashBalanceResponseDto getCashBalance() {
		User currentUser = userService.getCurrentUser();
		return new CashBalanceResponseDto(currentUser.getCashBalance().getBalance());
	}

	private TransactionHistoryResponseDto convertToResponse(CoinTransaction transaction) {
		return new TransactionHistoryResponseDto(transaction.getId(), transaction.getUser().getId(),
			transaction.getCoinInfo().getId(), transaction.getCoinInfo().getKrCoinName(),
			transaction.getCoinInfo().getTicker(), transaction.getOrderType(), transaction.getOrderQuantity(),
			transaction.getOrderPrice(), transaction.getOrderAmount(), transaction.getCreatedAt());
	}
}
