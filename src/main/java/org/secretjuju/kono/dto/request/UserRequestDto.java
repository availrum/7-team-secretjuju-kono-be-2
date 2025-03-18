package org.secretjuju.kono.dto.request;

import lombok.Getter;

@Getter
public class UserRequestDto {
	private int id;

	public UserRequestDto(int id) {
		this.id = id;
	}
}