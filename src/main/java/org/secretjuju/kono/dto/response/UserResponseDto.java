package org.secretjuju.kono.dto.response;

import org.secretjuju.kono.entity.User;

import lombok.Data;

@Data
public class UserResponseDto {
	private String nickname;
	private String profile;

	public UserResponseDto(User user) {
		this.nickname = user.getNickname();
		this.profile = user.getProfileImageUrl();
	}

}