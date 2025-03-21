package org.secretjuju.kono.controller;

import org.secretjuju.kono.dto.response.ApiResponseDto;
import org.secretjuju.kono.dto.response.DailyRankingResponseDto;
import org.secretjuju.kono.dto.response.TotalRankingResponseDto;
import org.secretjuju.kono.service.RankingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/rankings")
@RequiredArgsConstructor
public class RankingController {

	private final RankingService rankingService;

	@GetMapping
	public ResponseEntity<ApiResponseDto<TotalRankingResponseDto>> getTotalRanking() {
		ApiResponseDto<TotalRankingResponseDto> response = rankingService.getTotalRanking();
		return ResponseEntity.ok(response);
	}

	@GetMapping("/daily")
	public ResponseEntity<ApiResponseDto<DailyRankingResponseDto>> getDailyRanking() {
		ApiResponseDto<DailyRankingResponseDto> response = rankingService.getDailyRanking();
		return ResponseEntity.ok(response);
	}
}
