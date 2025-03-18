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
}