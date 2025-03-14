package org.secretjuju.kono.controller;

import java.net.URI;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.secretjuju.kono.annotation.Auth;
import org.secretjuju.kono.dto.request.KakaoTokenRequest;
import org.secretjuju.kono.dto.request.LoginRequest;
import org.secretjuju.kono.dto.response.CommonResponse;
import org.secretjuju.kono.service.AuthService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "인증", description = "인증 관련 API")
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@Value("${kakao.oauth.auth-url}")
	private String kakaoAuthUrl;

	@Value("${kakao.oauth.client-id}")
	private String clientId;

	@Value("${kakao.oauth.redirect-uri}")
	private String redirectUri;

	@Operation(
		summary = "카카오 이메일 회원가입",
		description = "카카오 OAuth 회원가입 요청을 처리합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "회원가입 성공",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"회원가입 성공\", \"data\": {}}")
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "잘못된 액세스 토큰",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"잘못된 액세스 토큰\"}")
			)
		),
		@ApiResponse(
			responseCode = "409",
			description = "이미 등록된 사용자",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"이미 등록된 사용자\"}")
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 오류 발생",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")
			)
		)
	})
	@PostMapping("/signup")
	public ResponseEntity<CommonResponse<?>> signUp(
		@Parameter(description = "카카오 액세스 토큰", required = true)
		@RequestBody KakaoTokenRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(CommonResponse.success("회원가입 성공", authService.kakaoSignup(request)));
	}

	@Operation(
		summary = "OAuth 액세스 토큰 요청",
		description = "인가 코드로 액세스 토큰을 요청합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "201",
			description = "토큰 발급 성공",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"토큰 발급 성공\", \"data\": {}}")
			)
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"잘못된 요청\"}")
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "인증 필요",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}")
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 내부 오류",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 내부 오류\"}")
			)
		)
	})
	@PostMapping("/oauth/kakao/token")
	public ResponseEntity<CommonResponse<?>> getKakaoToken(
		@Parameter(description = "카카오 인가 코드", required = true)
		@RequestBody KakaoTokenRequest request
	) {
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(CommonResponse.success(authService.getKakaoToken(request)));
	}

	@Operation(
		summary = "카카오 사용자 정보 요청",
		description = "카카오 사용자 정보를 가져옵니다.",
		security = @SecurityRequirement(name = "bearer-token")
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "200",
			description = "사용자 정보 반환",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"사용자 정보 반환\", \"data\": {}}")
			)
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"잘못된 요청\"}")
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "인증 필요",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}")
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 내부 오류",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 내부 오류\"}")
			)
		)
	})
	@GetMapping("/oauth/kakao/me")
	public ResponseEntity<CommonResponse<?>> getUserInfo(
		@Parameter(description = "Bearer 인증 토큰", required = true)
		@Auth String accessToken
	) {
		return ResponseEntity.ok(CommonResponse.success(authService.getUserInfo(accessToken)));
	}

	@Operation(
		summary = "서비스 로그인",
		description = "OAuth 로그인 후 JWT 토큰을 발급합니다."
	)
	@ApiResponses({
		@ApiResponse(
			responseCode = "201",
			description = "로그인 성공",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": true, \"message\": \"로그인 성공\", \"data\": {}}")
			)
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"잘못된 요청\"}")
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "인증 필요",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}")
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 내부 오류",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 내부 오류\"}")
			)
		)
	})
	@PostMapping("/oauth/kakao/login")
	public ResponseEntity<CommonResponse<?>> serviceLogin(
		@Parameter(description = "로그인 요청 정보", required = true)
		@RequestBody LoginRequest request
	) {
		return ResponseEntity.ok(CommonResponse.success(authService.serviceLogin(request)));
	}

	@Operation(
		summary = "OAuth 로그인 요청",
		description = "카카오 로그인 페이지로 리디렉트하여 인가 코드를 요청합니다."
	)
	@ApiResponses(value = {
		@ApiResponse(
			responseCode = "302",
			description = "인가 코드 발급 성공 (리디렉션)"
		),
		@ApiResponse(
			responseCode = "400",
			description = "잘못된 요청",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"잘못된 요청\"}")
			)
		),
		@ApiResponse(
			responseCode = "401",
			description = "인증 필요",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}")
			)
		),
		@ApiResponse(
			responseCode = "403",
			description = "접근 거부",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"접근 거부\"}")
			)
		),
		@ApiResponse(
			responseCode = "500",
			description = "서버 내부 오류",
			content = @Content(
				schema = @Schema(implementation = CommonResponse.class),
				examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 내부 오류\"}")
			)
		)
	})
	@GetMapping("/oauth/kakao")
	public ResponseEntity<Void> kakaoAuthRedirect() {
		String redirectUrl = String.format("%s?client_id=%s&redirect_uri=%s&response_type=code",
			kakaoAuthUrl, clientId, redirectUri);

		return ResponseEntity.status(HttpStatus.FOUND)
			.location(URI.create(redirectUrl))
			.build();
	}
}
