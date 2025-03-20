package org.secretjuju.kono.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "total_ranking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalRanking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "current_total_assets", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long currentTotalAssets; // 현재 총 자산

	@Column(name = "last_day_total_assets", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long lastDayTotalAssets; // 어제 23:59 기준 총 자산

	@Column(name = "profit_rate", columnDefinition = "DOUBLE DEFAULT 0.0")
	private Double profitRate; // 수익률

	@Column(name = "total_rank", columnDefinition = "INT DEFAULT 0")
	private Integer totalRank; // 전체 랭킹 순위

	@Column(name = "created_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime createdAt;

	public TotalRanking(User user) {
		this.user = user;
		this.currentTotalAssets = 0L;
		this.lastDayTotalAssets = 0L;
		this.profitRate = 0.0;
		this.totalRank = 0;
		this.createdAt = LocalDateTime.now();
	}
}