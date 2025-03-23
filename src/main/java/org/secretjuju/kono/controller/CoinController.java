package org.secretjuju.kono.controller;

import org.secretjuju.kono.dto.request.CoinRequestDto;
import org.secretjuju.kono.dto.request.CoinSellBuyRequestDto;
import org.secretjuju.kono.dto.response.CoinResponseDto;
import org.secretjuju.kono.service.CoinService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/coins")
public class CoinController {
	public final CoinService coinService;

	public CoinController(CoinService coinService) {
		this.coinService = coinService;
	}

	// 코인 특정 정보 조회?
	@GetMapping("/{ticker}")
	public CoinResponseDto getCoin(@PathVariable String ticker) {
		CoinRequestDto coinRequestDto = new CoinRequestDto(ticker);
		CoinResponseDto coinResponseDto = coinService.getCoinByName(coinRequestDto);
		return coinResponseDto;
	}

	@PostMapping("/orders")
	public ResponseEntity<Void> createCoinOrder(@RequestBody CoinSellBuyRequestDto coinSellBuyRequestDto) {
		coinService.createCoinOrder(coinSellBuyRequestDto);
		return ResponseEntity.ok().build();
	}
}