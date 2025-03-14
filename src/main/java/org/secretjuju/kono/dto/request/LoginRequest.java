package org.secretjuju.kono.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
	private String oauthProvider; // 예: "kakao"
	private String accessToken; // 카카오에서 발급된 액세스 토큰
}
