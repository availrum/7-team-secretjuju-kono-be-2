package org.secretjuju.kono.dto;

import lombok.Getter;

@Getter
public class UserRequestDto {
	private int id;

	public UserRequestDto(int id) {
		this.id = id;
	}
}