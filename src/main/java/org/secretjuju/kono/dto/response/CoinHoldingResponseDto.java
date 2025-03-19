package org.secretjuju.kono.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoinHoldingResponseDto {
	private String ticker;
	private String coinName;
	private Double holdingQuantity;
	private Double holdingPrice;
}