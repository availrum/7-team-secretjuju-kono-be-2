package org.secretjuju.kono.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CoinSellBuyRequestDto {
	private String ticker; // 코인 티커
	private String orderType; // "buy" 또는 "sell"
	private Long orderAmount; // 주문 금액 (원화)

	// 서비스에서 계산되는 필드
	private transient Double orderQuantity; // 코인 수량
	private transient Double orderPrice; // 코인 단가
}