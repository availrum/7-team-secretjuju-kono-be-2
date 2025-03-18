package org.secretjuju.kono.dto;

import lombok.Getter;

@Getter
public class CoinRequestDto {
	private String ticker;

	public CoinRequestDto(String ticker) {
		this.ticker = ticker;
	}
}
