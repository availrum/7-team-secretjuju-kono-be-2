package org.secretjuju.kono.dto.response;

import org.secretjuju.kono.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
	private String nickname;
	private String profile;

	public static UserResponseDto from(User user) {
		return UserResponseDto.builder().nickname(user.getNickname()).profile(user.getProfileImageUrl()).build();
	}
}