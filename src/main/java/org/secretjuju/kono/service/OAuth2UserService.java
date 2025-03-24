package org.secretjuju.kono.service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.secretjuju.kono.entity.CashBalance;
import org.secretjuju.kono.entity.User;
import org.secretjuju.kono.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class OAuth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	public OAuth2UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(userRequest);
		Map<String, Object> attributes = oAuth2User.getAttributes();

		Long kakaoId = Long.valueOf(attributes.get("id").toString());
		Map<String, Object> properties = (Map<String, Object>) attributes.get("properties");
		String nickname = (String) properties.get("nickname");
		String profileImageUrl = (String) properties.get("profile_image");

		// 사용자 존재 여부 확인 및 저장
		User user = userRepository.findByKakaoId(kakaoId).orElseGet(() -> saveUser(kakaoId, nickname, profileImageUrl));

		log.info("User logged in: {}", user.getNickname());
		// SecurityContext에 저장될 사용자 객체 반환
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority("ROLE_USER")), attributes, "id");
	}

	private User saveUser(Long kakaoId, String nickname, String profileImageUrl) {
		User user = new User();
		user.setKakaoId(kakaoId);
		user.setNickname(nickname);
		user.setProfileImageUrl(profileImageUrl);
		user.setCreatedAt(LocalDateTime.now());

		// CashBalance 생성 및 연결
		CashBalance cashBalance = new CashBalance();
		cashBalance.setUser(user);
		cashBalance.setBalance(10000000L);
		user.setCashBalance(cashBalance);

		log.info("New user saved: {}", nickname);
		return userRepository.save(user);
	}
}