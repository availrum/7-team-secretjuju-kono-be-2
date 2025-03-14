package org.secretjuju.kono.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rankings")
public class RankingController {

	@Operation(summary = "일간 랭킹 조회", description = "사용자들의 투자금 기준으로 정렬하여 일간 랭킹 조회 (1~100등). 5분 단위로 갱신됨.", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "일간 랭킹 조회 성공",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"일간 랭킹 조회 성공\", \"data\": []}")
			)
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청 (Invalid ranking)",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"잘못된 요청\"}")
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
	@GetMapping("/daily")
	public String getDailyRanking() {
		return "일간 랭킹 조회 엔드포인트";
	}

	@Operation(summary = "사용자 일간 랭킹 조회", description = "JWT 토큰을 이용하여 해당 사용자의 일간 랭킹 조회", security = @SecurityRequirement(name = "bearer-token"))
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
	public String getUserDailyRanking() {
		return "사용자 일간 랭킹 조회 엔드포인트";
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
	@GetMapping("")
	public String getTotalRanking() {
		return "전체 랭킹 조회 엔드포인트";
	}

	@Operation(summary = "사용자 전체 랭킹 조회", description = "JWT 토큰을 이용하여 해당 사용자의 전체 랭킹 조회", security = @SecurityRequirement(name = "bearer-token"))
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
	@GetMapping("/me")
	public String getUserTotalRanking() {
		return "사용자 전체 랭킹 조회 엔드포인트";
	}
}