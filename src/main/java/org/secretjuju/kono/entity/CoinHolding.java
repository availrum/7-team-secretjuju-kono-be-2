package org.secretjuju.kono.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "coin_holdings")
@Getter @Setter
@NoArgsConstructor
public class CoinHolding {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "coin_id", nullable = false)
	private CoinInfo coinInfo;

	@Column(name = "holding_quantity", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
	private Double holdingQuantity = 0.0; // 보유 코인 수량

	@Column(name = "holding_price", nullable = false, columnDefinition = "DOUBLE DEFAULT 0.0")
	private Double holdingPrice = 0.0; // 매수 금액
}
