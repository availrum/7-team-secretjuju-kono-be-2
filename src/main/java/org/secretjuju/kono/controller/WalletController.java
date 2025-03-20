package org.secretjuju.kono.controller;

import java.util.List;

import org.secretjuju.kono.dto.response.ApiResponseDto;
import org.secretjuju.kono.dto.response.CashBalanceResponseDto;
import org.secretjuju.kono.dto.response.CoinHoldingDetailResponseDto;
import org.secretjuju.kono.dto.response.CoinHoldingResponseDto;
import org.secretjuju.kono.dto.response.TransactionHistoryResponseDto;
import org.secretjuju.kono.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/wallets")
@RequiredArgsConstructor
public class WalletController {

	private final WalletService walletService;

	// 트렌잭션
	@GetMapping("/transactions")
	public ResponseEntity<ApiResponseDto<List<TransactionHistoryResponseDto>>> getTransactionHistory(
			@AuthenticationPrincipal OAuth2User principal) {
		log.info("Transaction history requested by user ID: {}", principal.getAttribute("id").toString());
		List<TransactionHistoryResponseDto> transactions = walletService.getTransactionHistory();
		return ResponseEntity.ok(new ApiResponseDto<>("Transaction history retrieved", transactions));
	}

	@GetMapping("/cash")
	public ResponseEntity<ApiResponseDto<CashBalanceResponseDto>> getCashBalance(
			@AuthenticationPrincipal OAuth2User principal) {
		log.info("Cash balance requested by user ID: {}", principal.getAttribute("id").toString());
		CashBalanceResponseDto cashBalance = walletService.getCashBalance();
		return ResponseEntity.ok(new ApiResponseDto<>("Cash data retrieved", cashBalance));
	}

	@GetMapping("/coins")
	public ResponseEntity<ApiResponseDto<List<CoinHoldingResponseDto>>> getCoinHoldings(
			@AuthenticationPrincipal OAuth2User principal) {
		log.info("Coin holdings requested by user ID: {}", principal.getAttribute("id").toString());
		List<CoinHoldingResponseDto> coinHoldings = walletService.getCoinHoldings();
		return ResponseEntity.ok(new ApiResponseDto<>("Coin holdings retrieved", coinHoldings));
	}

	@GetMapping("/coins/{ticker}")
	public ResponseEntity<ApiResponseDto<CoinHoldingDetailResponseDto>> getCoinHoldingDetail(
			@PathVariable String ticker, @AuthenticationPrincipal OAuth2User principal) {
		log.info("Coin detail for ticker {} requested by user ID: {}", ticker, principal.getAttribute("id").toString());
		CoinHoldingDetailResponseDto coinHoldingDetail = walletService.getCoinHoldingDetail(ticker);
		return ResponseEntity.ok(new ApiResponseDto<>("Coin detail data retrieved", coinHoldingDetail));
	}
}