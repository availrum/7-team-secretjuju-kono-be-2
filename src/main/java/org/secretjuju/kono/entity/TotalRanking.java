package org.secretjuju.kono.entity;

import java.time.ZonedDateTime;

import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "total_ranking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TotalRanking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, unique = true)
	private User user;

	@Column(name = "current_total_assets", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long currentTotalAssets; // 현재 총 자산

	@Column(name = "last_day_total_assets", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long lastDayTotalAssets; // 어제 23:59 기준 총 자산

	@Column(name = "profit_rate", columnDefinition = "DOUBLE DEFAULT 0.0")
	private Double profitRate; // 수익률

	@Column(name = "total_rank", columnDefinition = "INT DEFAULT 0")
	private Integer totalRank; // 전체 랭킹 순위

	@Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private ZonedDateTime updatedAt;

	// 초기 자산 금액 (모든 사용자는 1천만원으로 시작)
	public static final long INITIAL_ASSET = 10000000L;

	public TotalRanking(User user) {
		this.user = user;
		this.currentTotalAssets = 0L;
		this.lastDayTotalAssets = 0L;
		this.profitRate = 0.0;
		this.totalRank = 0;
		this.updatedAt = ZonedDateTime.now();
	}

	public void updateTime() {
		this.updatedAt = ZonedDateTime.now();
	}

	// 수익금 계산 (현재 총 자산 - 초기 자산(1천만원))
	public Long getProfit() {
		return this.currentTotalAssets - INITIAL_ASSET;
	}
}