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
@RequestMapping("/api/v1/coins/orders")
public class OrderController {

	@Operation(summary = "코인 매도/매수", description = "사용자가 코인을 매도하거나 매수하는 거래 실행. 최소 주문 수량: 0.0001, 최소 주문 금액: 1000원, 최대 주문 금액: 100조.", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "거래 성공",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"거래 성공\", \"data\": {}}")
			)
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 주문 요청",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"잘못된 주문 요청\"}")
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
			description = "주문 정보 없음",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"주문 정보 없음\"}")
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
	@PostMapping
	public String placeOrder() {
		return "코인 매도/매수 주문 실행 엔드포인트";
	}
}