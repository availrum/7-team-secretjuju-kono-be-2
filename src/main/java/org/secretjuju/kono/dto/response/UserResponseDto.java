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
	private Integer id;
	private String nickname;
	private String profile;
	private String role;

	public static UserResponseDto from(User user) {
		return UserResponseDto.builder().id(user.getId()).nickname(user.getNickname())
				.profile(user.getProfileImageUrl()).role(user.getRole()).build();
	}
}