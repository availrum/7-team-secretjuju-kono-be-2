package org.secretjuju.kono.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CoinSellBuyRequestDto {
	private String ticker;
	private String orderType;
	private Double orderQuantity;
	private Long orderAmount;
	private Double orderPrice;
}