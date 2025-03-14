package org.secretjuju.kono.service;

import org.secretjuju.kono.dto.request.KakaoTokenRequest;
import org.secretjuju.kono.dto.response.KakaoUserInfoResponse;
import org.secretjuju.kono.dto.request.LoginRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

	// 1️⃣ 카카오 회원가입 (Stub)
	public String kakaoSignup(KakaoTokenRequest request) {
		return "회원가입 성공 (Stub)";
	}

	// 2️⃣ 액세스 토큰 요청 (Stub)
	public String getKakaoToken(KakaoTokenRequest request) {
		return "액세스 토큰 발급 성공 (Stub)";
	}

	// 3️⃣ 사용자 정보 요청 (Stub)
	public KakaoUserInfoResponse getUserInfo(String accessToken) {
		KakaoUserInfoResponse response = new KakaoUserInfoResponse();
		response.setId(123456789L);
		response.setNickname("테스트 유저");
		response.setEmail("testuser@example.com");
		response.setProfileImageUrl("https://example.com/profile.jpg");
		return response;
	}

	// 4️⃣ JWT 로그인 (Stub)
	public String serviceLogin(LoginRequest request) {
		return "JWT 토큰 발급 성공 (Stub)";
	}
}