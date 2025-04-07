package org.secretjuju.kono.controller;

import org.secretjuju.kono.dto.response.TickerResponse;
import org.secretjuju.kono.service.UpbitService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UpbitController {

	private final UpbitService upbitService;

	public UpbitController(UpbitService upbitService) {
		this.upbitService = upbitService;
	}

	@GetMapping("/ticker/{market}")
	public ResponseEntity<TickerResponse> getTicker(@PathVariable String market) {
		TickerResponse ticker = upbitService.getTicker(market);
		return ResponseEntity.ok(ticker);
	}
}