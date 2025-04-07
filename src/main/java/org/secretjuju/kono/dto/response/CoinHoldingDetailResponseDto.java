package org.secretjuju.kono.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CoinHoldingDetailResponseDto {
	private boolean hasHolding;
	private double holdingQuantity;

}