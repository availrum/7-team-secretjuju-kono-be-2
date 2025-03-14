package org.secretjuju.kono.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoUserInfoResponse {
	private Long id; // 카카오 유저 ID
	private String nickname;
	private String email;
	private String profileImageUrl;
}