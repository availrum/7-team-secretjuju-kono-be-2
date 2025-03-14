package org.secretjuju.kono.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class KakaoTokenRequest {
	private String clientId;
	private String clientSecret;
	private String code;
	private String redirectUri;
	private String grantType;
}
