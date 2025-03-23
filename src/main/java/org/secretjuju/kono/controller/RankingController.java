package org.secretjuju.kono.controller;

import java.util.List;

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
	public ResponseEntity<ApiResponseDto<List<TotalRankingResponseDto>>> getTotalRanking() {

		List<TotalRankingResponseDto> totalRankingResponseDtoList = rankingService.getTotalRanking();
		return ResponseEntity.ok(new ApiResponseDto<>("Total ranking retrieved", totalRankingResponseDtoList));
	}

	@GetMapping("/daily")
	public ResponseEntity<ApiResponseDto<List<DailyRankingResponseDto>>> getDailyRanking() {

		List<DailyRankingResponseDto> dailyRankingResponseDtoList = rankingService.getDailyRanking();
		return ResponseEntity.ok(new ApiResponseDto<>("Daily ranking retrieved", dailyRankingResponseDtoList));
	}
}
