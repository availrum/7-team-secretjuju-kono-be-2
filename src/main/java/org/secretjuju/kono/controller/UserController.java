package org.secretjuju.kono.controller;

import java.util.List;

import org.secretjuju.kono.dto.request.UserRequestDto;
import org.secretjuju.kono.dto.response.FavoriteCoinsResponseDto;
import org.secretjuju.kono.dto.response.UserResponseDto;
import org.secretjuju.kono.entity.CoinInfo;
import org.secretjuju.kono.service.CoinFavoriteService;
import org.secretjuju.kono.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
// @RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final CoinFavoriteService coinFavoriteService;
	public UserController(UserService userService, CoinFavoriteService coinFavoriteService) {
		this.userService = userService;
		this.coinFavoriteService = coinFavoriteService;
	}

	@GetMapping("/user")
	public UserResponseDto getUsernickname(@RequestParam Integer userId){
		UserRequestDto userRequestDto = new UserRequestDto(userId);
		UserResponseDto userResponseDto = userService.getUserById(userRequestDto);
		return userResponseDto;
	}

	@GetMapping("/users/favorites")
	public ResponseEntity<FavoriteCoinsResponseDto> getUserFavorites(@RequestParam Integer userId) {
		// 사용자의 관심 코인 목록 조회
		List<CoinInfo> favoriteCoins = coinFavoriteService.findFavoriteCoinsByUserId(userId);
		
		// 관심 코인이 없는 경우 204 No Content 반환
		if (favoriteCoins == null || favoriteCoins.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		
		// Entity를 응답 DTO로 변환
		FavoriteCoinsResponseDto responseDto = FavoriteCoinsResponseDto.fromEntityList(favoriteCoins);
		return ResponseEntity.ok(responseDto);
	}

	
	// 관심 코인 등록
	@PostMapping("/users/favorites/{ticker}")
	public ResponseEntity<Void> addFavoriteCoin(
			@RequestParam Integer userId,
			@PathVariable String ticker) {
		coinFavoriteService.addFavoriteCoin(userId, ticker);
		return ResponseEntity.ok().build();
	}

	// 관심 코인 삭제
	@DeleteMapping("/users/favorites/{ticker}")
	public ResponseEntity<Void> deleteFavoriteCoin(
			@RequestParam Integer userId,
			@PathVariable String ticker) {
		coinFavoriteService.deleteFavoriteCoin(userId, ticker);
		return ResponseEntity.ok().build();
	}
}