package org.secretjuju.kono.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cash_balance")
@Getter
@Setter
@NoArgsConstructor
public class CashBalance {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(name = "balance", nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 10000000")
	private Long balance = 10000000L; // 현금 잔액

	@Column(name = "total_invest", nullable = false, columnDefinition = "BIGINT UNSIGNED DEFAULT 0")
	private Long totalInvest = 0L; // 총 투자금액

}
