package org.secretjuju.kono.controller;

import java.util.List;

import org.secretjuju.kono.dto.request.NicknameUpdateRequest;
import org.secretjuju.kono.dto.request.UserRequestDto;
import org.secretjuju.kono.dto.response.FavoriteCoinsResponseDto;
import org.secretjuju.kono.dto.response.UserResponseDto;
import org.secretjuju.kono.entity.CoinInfo;
import org.secretjuju.kono.service.CoinFavoriteService;
import org.secretjuju.kono.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
// @RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final CoinFavoriteService coinFavoriteService;
	public UserController(UserService userService, CoinFavoriteService coinFavoriteService) {
		this.userService = userService;
		this.coinFavoriteService = coinFavoriteService;
	}

	@GetMapping("")
	public UserResponseDto getUsernickname(@AuthenticationPrincipal OAuth2User oauth2User) {
		Long kakaoId = Long.valueOf(oauth2User.getAttribute("id").toString());
		UserRequestDto userRequestDto = new UserRequestDto(kakaoId);
		UserResponseDto userResponseDto = userService.getUserById(userRequestDto);
		return userResponseDto;
	}

	@PutMapping("/nickname")
	public ResponseEntity<UserResponseDto> updateNickname(@AuthenticationPrincipal OAuth2User oauth2User,
			@Valid @RequestBody NicknameUpdateRequest request) {
		if (oauth2User == null) {
			return ResponseEntity.status(401).build();
		}

		Long kakaoId = Long.valueOf(oauth2User.getAttribute("id").toString());

		UserResponseDto updatedUser = userService.updateNickname(kakaoId, request);

		return ResponseEntity.ok(updatedUser);
	}

	@GetMapping("/favorites")
	public ResponseEntity<FavoriteCoinsResponseDto> getUserFavorites(@AuthenticationPrincipal OAuth2User oauth2User) {
		// 사용자의 관심 코인 목록 조회
		Long kakaoId = Long.valueOf(oauth2User.getAttribute("id").toString());
		List<CoinInfo> favoriteCoins = coinFavoriteService.findFavoriteCoinsByUserId(kakaoId);

		// 관심 코인이 없는 경우 204 No Content 반환
		if (favoriteCoins == null || favoriteCoins.isEmpty()) {
			return ResponseEntity.noContent().build();
		}

		// Entity를 응답 DTO로 변환
		FavoriteCoinsResponseDto responseDto = FavoriteCoinsResponseDto.fromEntityList(favoriteCoins);
		return ResponseEntity.ok(responseDto);
	}

	// 관심 코인 등록
	@PostMapping("/favorites/{ticker}")
	public ResponseEntity<Void> addFavoriteCoin(@RequestParam Integer userId, @PathVariable String ticker) {
		coinFavoriteService.addFavoriteCoin(userId, ticker);
		return ResponseEntity.ok().build();
	}

	// 관심 코인 삭제
	@DeleteMapping("/favorites/{ticker}")
	public ResponseEntity<Void> deleteFavoriteCoin(@RequestParam Integer userId, @PathVariable String ticker) {
		coinFavoriteService.deleteFavoriteCoin(userId, ticker);
		return ResponseEntity.ok().build();
	}
	// 회원 탈퇴
	@DeleteMapping("/withdraw")
	public ResponseEntity<Void> withdrawUser(@AuthenticationPrincipal OAuth2User oauth2User, HttpSession session) {
		if (oauth2User == null) {
			return ResponseEntity.status(401).build();
		}

		try {
			Long kakaoId = Long.valueOf(oauth2User.getAttribute("id").toString());
			log.info("회원탈퇴 요청: kakaoId={}", kakaoId);

			// UserService의 withdrawUser 메서드 호출
			userService.withdrawUser(kakaoId);

			// 세션 무효화
			session.invalidate();

			return ResponseEntity.ok().build();

		} catch (Exception e) {
			log.error("회원탈퇴 처리 중 오류 발생", e);
			return ResponseEntity.status(500).build();
		}
	}
}