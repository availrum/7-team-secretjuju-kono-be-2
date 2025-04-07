package org.secretjuju.kono.dto.response;

import java.time.ZonedDateTime;

import org.secretjuju.kono.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
	private String nickname;
	private String profileImageUrl;
	private ZonedDateTime createdAt;

	public static UserResponseDto from(User user) {
		return UserResponseDto.builder().nickname(user.getNickname()).profileImageUrl(user.getProfileImageUrl())
				.createdAt(user.getCreatedAt()).build();
	}

	@Builder
	public UserResponseDto(String nickname, String profileImageUrl, ZonedDateTime createdAt) {
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.createdAt = createdAt;
	}
}