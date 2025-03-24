package org.secretjuju.kono.dto.response;

import org.secretjuju.kono.entity.CoinInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoinInfoResponseDto {
	private String ticker;
	private String coinName;

	public static CoinInfoResponseDto fromEntity(CoinInfo coinInfo) {
		return CoinInfoResponseDto.builder().ticker(coinInfo.getTicker()).coinName(coinInfo.getKrCoinName()).build();
	}
}