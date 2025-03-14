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
@RequestMapping("/api/v1/wallets")
public class WalletController {

	@Operation(summary = "보유 코인 목록 조회", description = "사용자가 보유한 코인의 목록을 조회합니다.", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "보유 코인 목록 조회 성공", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": true, \"message\": \"보유 코인 목록 조회 성공\", \"data\": []}"))),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")))})
	@GetMapping("/holdings")
	public String getCoinHoldings() {
		return """
				{
				    "message": "Coin holdings retrieved successfully",
				    "data": [
				        {
				            "ticker": "BTC",
				            "name": "Bitcoin",
				            "logo": "https://example.com/images/coins/btc.png",
				            "holdingQuantity": 1.25
				        }
				    ]
				}
				""";
	}

	@Operation(summary = "특정 코인 보유 정보 조회", description = "사용자가 특정 코인을 보유하고 있는지 확인합니다.", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "특정 코인 보유 정보 조회 성공", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": true, \"message\": \"특정 코인 보유 정보 조회 성공\", \"data\": {\"hasHolding\": true}}"))),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = String.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")))})
	@GetMapping("/{coinId}")
	public String getCoinDetail(@PathVariable int coinId) {
		return """
				{
				    "message": "Get coin detail data success",
				    "data": {
				        "hasHolding": true
				    }
				}
				""";
	}
}