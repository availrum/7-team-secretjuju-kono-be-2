package org.secretjuju.kono.controller;

import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api/v1/coins")
public class CoinController {

	@Operation(summary = "코인 목록 조회", description = "페이지네이션 적용된 코인 목록을 조회", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "코인 목록 조회 성공", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": true, \"message\": \"코인 목록 조회 성공\", \"data\": []}"))),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")))})
	@GetMapping
	public String getCoinList() {
		return "코인 목록 조회 엔드포인트";
	}

	@Operation(summary = "코인 상세 정보 조회", description = "특정 코인의 상세 정보를 조회", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "코인 상세 정보 조회 성공", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": true, \"message\": \"코인 상세 정보 조회 성공\", \"data\": {}}"))),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "404", description = "코인 정보 없음", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"코인 정보 없음\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")))})
	@GetMapping("/{ticker}")
	public String getCoinDetail(@PathVariable Long coinId) {
		return "코인 상세 정보 조회 엔드포인트";
	}
}