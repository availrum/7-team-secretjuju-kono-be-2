package org.secretjuju.kono.service;

import java.util.Optional;

import org.secretjuju.kono.dto.request.UserRequestDto;
import org.secretjuju.kono.dto.response.UserResponseDto;
import org.secretjuju.kono.entity.User;
import org.secretjuju.kono.repository.UserRepository;
import org.springframework.stereotype.Service;
@Service
public class UserService {

	private final UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	// 🔹 userId로 사용자 정보 조회
	public UserResponseDto getUserById(UserRequestDto userRequestDto) {
		Optional<User> user = userRepository.findById(userRequestDto.getId());
		UserResponseDto userResponseDto = new UserResponseDto(user.orElse(null));
		return userResponseDto;
	}

	// 현재 로그인한 사용자 정보 조회
	public User getCurrentUser() {
		// TODO: 실제 인증 구현 후에는 SecurityContext에서 현재 사용자 정보를 가져와야 합니다.
		// 임시로 ID가 1인 사용자를 반환
		return userRepository.findById(1).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
	}
}