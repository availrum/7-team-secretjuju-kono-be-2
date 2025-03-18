package org.secretjuju.kono.controller;

import org.secretjuju.kono.dto.request.UserRequestDto;
import org.secretjuju.kono.dto.response.UserResponseDto;
import org.secretjuju.kono.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
// @RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/user")
	public UserResponseDto getUsernickname() {
		UserRequestDto userRequestDto = new UserRequestDto(1);
		UserResponseDto userResponseDto = userService.getUserById(userRequestDto);
		return userResponseDto;
	}
}