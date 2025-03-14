package org.secretjuju.kono.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users/wallets/transactions")
public class TransactionController {

	@Operation(summary = "사용자 매매 내역 조회", description = "사용자의 매도/매수 거래 내역을 조회합니다.", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "거래 내역 조회 성공",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"거래 내역 조회 성공\", \"data\": []}")
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "인증 필요",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}")
			)
		),
		@ApiResponse(
			responseCode = "403",
			description = "권한 없음",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"권한 없음\"}")
			)
		),
		@ApiResponse(
			responseCode = "404",
			description = "거래 내역 없음",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"거래 내역 없음\"}")
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 오류 발생",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")
			)
		)
	})
	@GetMapping
	public Map<String, Object> getTransactionHistory() {
		return Map.of(
			"success", true,
			"message", "거래 내역 조회 성공",
			"data", new Object[] {} // 거래 내역이 비어있다고 가정
		);
	}

	@Operation(summary = "사용자의 일간 랭킹 조회", description = "JWT 토큰을 이용하여 해당 사용자의 일간 랭킹을 조회", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "사용자의 일간 랭킹 조회 성공",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"사용자의 일간 랭킹 조회 성공\", \"data\": {}}")
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "인증 필요",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}")
			)
		),
		@ApiResponse(
			responseCode = "404",
			description = "사용자 정보 없음",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"사용자 정보 없음\"}")
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 오류 발생",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")
			)
		)
	})
	@GetMapping("/daily/me")
	public Map<String, Object> getUserDailyRanking() {
		return Map.of(
			"success", true,
			"message", "사용자의 일간 랭킹 조회 성공",
			"data", Map.of()
		);
	}

	@Operation(summary = "전체 랭킹 조회", description = "사용자들의 투자금 기준으로 정렬하여 전체 랭킹 조회 (1~100등)", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "전체 랭킹 조회 성공",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"전체 랭킹 조회 성공\", \"data\": []}")
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "인증 필요",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}")
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 오류 발생",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")
			)
		)
	})
	@GetMapping("/ranking")
	public Map<String, Object> getTotalRanking() {
		return Map.of(
			"success", true,
			"message", "전체 랭킹 조회 성공",
			"data", new Object[] {}
		);
	}

	@Operation(summary = "사용자의 전체 랭킹 조회", description = "JWT 토큰을 이용하여 해당 사용자의 전체 랭킹 조회", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "사용자의 전체 랭킹 조회 성공",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"사용자의 전체 랭킹 조회 성공\", \"data\": {}}")
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "인증 필요",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}")
			)
		),
		@ApiResponse(
			responseCode = "404",
			description = "사용자 정보 없음",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"사용자 정보 없음\"}")
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 오류 발생",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")
			)
		)
	})
	@GetMapping("/ranking/me")
	public Map<String, Object> getUserTotalRanking() {
		return Map.of(
			"success", true,
			"message", "사용자의 전체 랭킹 조회 성공",
			"data", Map.of()
		);
	}
}
