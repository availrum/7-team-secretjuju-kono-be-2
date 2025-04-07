package org.secretjuju.kono.dto.request;

import lombok.Getter;

@Getter
public class CoinRequestDto {
	private String ticker;

	public CoinRequestDto(String ticker) {
		this.ticker = ticker;
	}
}
