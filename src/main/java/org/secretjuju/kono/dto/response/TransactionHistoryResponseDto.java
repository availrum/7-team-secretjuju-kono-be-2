package org.secretjuju.kono.dto.response;

import java.time.ZonedDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryResponseDto {
	private Integer transactionId;
	private Integer userId;
	private Integer coinId;
	private String coinName;
	private String ticker;
	private String orderType;
	private Double orderQuantity;
	private Double orderPrice;
	private Long orderAmount;
	private ZonedDateTime createdAt;
}