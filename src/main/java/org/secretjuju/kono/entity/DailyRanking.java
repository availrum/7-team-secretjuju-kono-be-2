package org.secretjuju.kono.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "daily_ranking")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DailyRanking {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(name = "current_total_assets", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long currentTotalAssets; // 현재 총 자산

	@Column(name = "last_day_total_assets", nullable = false, columnDefinition = "BIGINT UNSIGNED")
	private Long lastDayTotalAssets; // 어제 23:59 기준 총 자산

	@Column(name = "profit_rate", columnDefinition = "DOUBLE DEFAULT 0.0")
	private Double profitRate; // 수익률

	@Column(name = "daily_rank", columnDefinition = "INT DEFAULT 0")
	private Integer dailyRank; // 일간 랭킹 순위

	@Column(name = "updated_at", nullable = false, columnDefinition = "DATETIME DEFAULT CURRENT_TIMESTAMP")
	private LocalDateTime updatedAt;

	public DailyRanking(User user) {
		this.user = user;
		this.currentTotalAssets = 0L;
		this.lastDayTotalAssets = 0L;
		this.profitRate = 0.0;
		this.dailyRank = 0;
		this.updatedAt = LocalDateTime.now();
	}

	public void updateTime() {
		this.updatedAt = LocalDateTime.now();
	}
}