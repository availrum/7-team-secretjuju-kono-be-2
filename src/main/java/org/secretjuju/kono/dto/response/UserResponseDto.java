package org.secretjuju.kono.dto.response;

import java.time.LocalDateTime;

import org.secretjuju.kono.entity.User;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
	private Integer id;
	private String nickname;
	private String profileImageUrl;
	private LocalDateTime createdAt;

	public static UserResponseDto from(User user) {
		return UserResponseDto.builder().id(user.getId()).nickname(user.getNickname())
				.profileImageUrl(user.getProfileImageUrl()).createdAt(user.getCreatedAt()).build();
	}

	@Builder
	public UserResponseDto(Integer id, String nickname, String profileImageUrl, LocalDateTime createdAt) {
		this.id = id;
		this.nickname = nickname;
		this.profileImageUrl = profileImageUrl;
		this.createdAt = createdAt;
	}
}