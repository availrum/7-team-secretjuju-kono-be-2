package org.secretjuju.kono.service;

import java.util.Optional;

import org.secretjuju.kono.dto.request.NicknameUpdateRequest;
import org.secretjuju.kono.dto.request.UserRequestDto;
import org.secretjuju.kono.dto.response.UserResponseDto;
import org.secretjuju.kono.entity.User;
import org.secretjuju.kono.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
	private final WebClient webClient;
	private final UserRepository userRepository;

	@Value("${kakao.admin.key}")
	private String adminKey;

	public UserService(WebClient webClient, UserRepository userRepository) {
		this.webClient = webClient;
		this.userRepository = userRepository;
	}

	@Transactional
	public UserResponseDto updateNickname(Long kakaoId, NicknameUpdateRequest request) {
		User user = getUserByKakaoId(kakaoId);
		user.setNickname(request.getNickname());

		User savedUser = userRepository.save(user);
		return UserResponseDto.from(savedUser);
	}

	// userId로 사용자 정보 조회
	public UserResponseDto getUserById(UserRequestDto userRequestDto) {
		Optional<User> user = userRepository.findById(userRequestDto.getId());
		return user.map(UserResponseDto::from).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
	}

	public User getUserByKakaoId(Long kakaoId) {
		return userRepository.findByKakaoId(kakaoId).orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
	}

	// 현재 로그인한 사용자 정보 조회
	public User getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			throw new RuntimeException("인증된 사용자가 없습니다.");
		}

		if (authentication.getPrincipal() instanceof OAuth2User) {
			OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
			Long kakaoId = Long.valueOf(oAuth2User.getAttribute("id").toString());
			return getUserByKakaoId(kakaoId);
		}

		throw new RuntimeException("지원되지 않는 인증 방식입니다.");
	}

	// 현재 로그인한 사용자 정보를 DTO로 반환
	public UserResponseDto getCurrentUserDto() {
		User currentUser = getCurrentUser();
		return UserResponseDto.from(currentUser);
	}
	@Transactional
	public void withdrawUser(Long kakaoId) {
		try {
			String adminKeyHeader = "KakaoAK " + adminKey;

			webClient.post().uri("/v1/user/unlink").header("Authorization", adminKeyHeader)
					.contentType(MediaType.APPLICATION_FORM_URLENCODED)
					.body(BodyInserters.fromFormData("target_id_type", "user_id").with("target_id", kakaoId.toString()))
					.retrieve().bodyToMono(String.class).block();

			log.info("카카오 연결끊기 성공: kakaoId={}", kakaoId);
			userRepository.deleteByKakaoId(kakaoId);

			log.info("DB 사용자 정보 삭제 성공: kakaoId={}", kakaoId);

		} catch (WebClientResponseException e) {
			log.error("카카오 연결끊기 API 호출 실패: status={}, body={}", e.getStatusCode(), e.getResponseBodyAsString(), e);
			throw new RuntimeException("카카오 연결끊기 실패", e);
		} catch (Exception e) {
			log.error("회원탈퇴 처리 중 오류 발생: kakaoId={}", kakaoId, e);
			throw new RuntimeException("회원탈퇴 처리 중 오류가 발생했습니다.", e);
		}
	}

}