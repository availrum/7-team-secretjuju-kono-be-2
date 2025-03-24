package org.secretjuju.kono.dto.request;

import lombok.Getter;

@Getter
public class UserRequestDto {
	private long id;

	public UserRequestDto(long kakaoid) {
		this.id = kakaoid;
	}
}