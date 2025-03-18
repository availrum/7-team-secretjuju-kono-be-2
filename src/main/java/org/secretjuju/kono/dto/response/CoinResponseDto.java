package org.secretjuju.kono.dto.response;

import org.secretjuju.kono.entity.CoinInfo;

import lombok.Data;

@Data
public class CoinResponseDto {
	private String ticker;
	private String kr_coin_name;


	public CoinResponseDto(CoinInfo coinInfo) {
		this.ticker = coinInfo.getTicker();
		this.kr_coin_name = coinInfo.getKrCoinName();
	}

}