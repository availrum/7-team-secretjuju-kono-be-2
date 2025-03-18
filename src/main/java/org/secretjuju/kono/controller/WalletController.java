package org.secretjuju.kono.controller;

import java.util.List;

import org.secretjuju.kono.dto.response.ApiResponseDto;
import org.secretjuju.kono.dto.response.CashBalanceResponseDto;
import org.secretjuju.kono.dto.response.CoinHoldingResponseDto;
import org.secretjuju.kono.dto.response.TransactionHistoryResponseDto;
import org.secretjuju.kono.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

	private final WalletService walletService;

	@GetMapping("/transactions")
	public ResponseEntity<ApiResponseDto<List<TransactionHistoryResponseDto>>> getTransactionHistory() {
		List<TransactionHistoryResponseDto> transactions = walletService.getTransactionHistory();
		return ResponseEntity.ok(new ApiResponseDto<>("Transaction history retrieved", transactions));
	}

	@GetMapping("/cash")
	public ResponseEntity<ApiResponseDto<CashBalanceResponseDto>> getCashBalance() {
		CashBalanceResponseDto cashBalance = walletService.getCashBalance();
		return ResponseEntity.ok(new ApiResponseDto<>("Cash data retrieved", cashBalance));
	}

	@GetMapping("/coins")
	public ResponseEntity<ApiResponseDto<List<CoinHoldingResponseDto>>> getCoinHoldings() {
		List<CoinHoldingResponseDto> coinHoldings = walletService.getCoinHoldings();
		return ResponseEntity.ok(new ApiResponseDto<>("Coin holdings retrieved", coinHoldings));
	}
}
