package org.secretjuju.kono.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import java.util.Map;

@RestController
@RequestMapping("/api/cash")
public class CashController {

	@Operation(
		summary = "현금 잔액 조회",
		description = "사용자의 현금 잔액을 조회합니다.",
		security = @SecurityRequirement(name = "bearer-token")
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "200",
			description = "현금 잔액 조회 성공",
			content = @Content(
				schema = @Schema(implementation = String.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"현금 잔액 조회 성공\", \"data\": {\"cash\": 500000}}")
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
	@GetMapping
	public ResponseEntity<Map<String, Object>> getCashBalance() {
		// 예제 데이터 (실제로는 DB에서 조회)
		Map<String, Object> response = Map.of(
			"success", true,
			"message", "현금 잔액 조회 성공",
			"data", Map.of("cash", 500000)
		);
		return ResponseEntity.ok(response);
	}
}
