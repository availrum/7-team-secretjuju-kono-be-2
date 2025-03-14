package org.secretjuju.kono.controller;

import java.util.HashMap;
import java.util.Map;

import org.secretjuju.kono.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "user-controller", description = "회원 관련 API")
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@Operation(summary = "회원 정보 조회", description = "사용자의 정보를 조회", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "회원 정보 조회 성공", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": true, \"message\": \"회원 정보 조회 성공\", \"data\": {}}"))),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "404", description = "사용자 정보 없음", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"사용자 정보 없음\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")))})
	@GetMapping
	public String getUserInfo() {
		return "회원 정보 조회 엔드포인트";
	}

	@Operation(summary = "회원 탈퇴", description = "회원 계정을 삭제", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "회원 탈퇴 성공"),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "404", description = "사용자 정보 없음", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"사용자 정보 없음\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")))})
	@DeleteMapping
	public String deleteUser() {
		return "회원 탈퇴 엔드포인트";
	}

	@Operation(summary = "관심 코인 조회", description = "사용자의 관심 코인 목록 조회, 없을 경우 NULL 반환 이후 탐색 버튼 생성", security = @SecurityRequirement(name = "bearer-token"))
	@GetMapping("/favorites")
	public String getUserFavoriteCoins() {
		return "관심 코인 조회 엔드포인트";
	}

	@Operation(summary = "관심 코인 추가", description = "사용자의 관심 코인 목록에 새로운 코인을 추가", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "관심 코인 추가 성공", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": true, \"message\": \"관심 코인 추가 성공\", \"data\": {}}"))),
			@ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"잘못된 요청\"}"))),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"권한 없음\"}"))),
			@ApiResponse(responseCode = "404", description = "사용자 정보 없음", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"사용자 정보 없음\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")))})
	@PostMapping("/favorites/{ticker}")
	public String addUserFavoriteCoin() {
		return "관심 코인 추가 엔드포인트";
	}

	@Operation(summary = "관심 코인 삭제", description = "사용자의 관심 코인 목록에서 특정 코인을 삭제", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {@ApiResponse(responseCode = "204", description = "관심 코인 삭제 성공"),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "403", description = "권한 없음", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"권한 없음\"}"))),
			@ApiResponse(responseCode = "404", description = "사용자 정보 없음", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"사용자 정보 없음\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")))})
	@DeleteMapping("/favorites/{ticker}")
	public String removeUserFavoriteCoin(@PathVariable Long favoriteId) {
		return "관심 코인 삭제 엔드포인트";
	}

	@Operation(summary = "회원 뱃지 목록 조회", description = "사용자가 보유한 뱃지 목록을 조회합니다.", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "뱃지 목록 조회 성공", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": true, \"message\": \"뱃지 목록 조회 성공\", \"data\": {}}"))),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "404", description = "사용자 정보 없음", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"사용자 정보 없음\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 오류 발생", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 오류 발생\"}")))})
	@GetMapping("/badges")
	public String getUserBadges() {
		return "회원 뱃지 목록 조회 엔드포인트";
	}

	@Operation(summary = "회원 프로필 이미지 변경", description = "회원의 프로필 이미지를 변경합니다.", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "프로필 이미지 변경 성공", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": true, \"message\": \"프로필 이미지 변경 성공\", \"data\": {}}"))),
			@ApiResponse(responseCode = "404", description = "프로필 이미지 없음", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"프로필 이미지 없음\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 내부 오류\"}")))})
	@PostMapping("/profile")
	public ResponseEntity<Map<String, Object>> updateProfileImage(@RequestHeader("Authorization") String accessToken,
			@Valid @RequestBody Map<String, String> request) {
		return ResponseEntity.ok(new HashMap<>());
	}

	@Operation(summary = "회원 닉네임 변경", description = "회원의 닉네임을 변경합니다. 닉네임은 중복 불가하며, 띄어쓰기 및 특수문자 입력이 불가능합니다.", security = @SecurityRequirement(name = "bearer-token"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "닉네임 변경 성공", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": true, \"message\": \"닉네임 변경 성공\", \"data\": {}}"))),
			@ApiResponse(responseCode = "400", description = "닉네임 중복", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"닉네임 중복\"}"))),
			@ApiResponse(responseCode = "401", description = "인증 필요", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"인증 필요\"}"))),
			@ApiResponse(responseCode = "409", description = "닉네임 이미 사용 중", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"닉네임 이미 사용 중\"}"))),
			@ApiResponse(responseCode = "500", description = "서버 내부 오류", content = @Content(schema = @Schema(implementation = Map.class), examples = @ExampleObject(value = "{\"success\": false, \"message\": \"서버 내부 오류\"}")))})
	@PutMapping("/nickname")
	public ResponseEntity<Map<String, Object>> updateNickname(@RequestHeader("Authorization") String accessToken,
			@Valid @RequestBody Map<String, String> request) {
		return ResponseEntity.ok(new HashMap<>());
	}
}
