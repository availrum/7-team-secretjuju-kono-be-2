package org.secretjuju.kono.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserProfileResponse {
	private Long userId;
	private String nickname;
	private String profileImageUrl;
	private String createdAt;
}