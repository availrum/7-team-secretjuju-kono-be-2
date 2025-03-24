package org.secretjuju.kono.config;

import org.secretjuju.kono.service.RankingService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Configuration
@EnableScheduling
@Slf4j
public class RankingScheduler {
	private final RankingService rankingService;

	public RankingScheduler(RankingService rankingService) {
		this.rankingService = rankingService;
	}

	@Scheduled(fixedRate = 300000) // 5분마다 실행
	public void updateRankings() {
		try {
			rankingService.updateRankings();
		} catch (Exception e) {
			log.error("랭킹 업데이트 중 오류 발생", e);
		}
	}

	@Scheduled(cron = "0 59 23 * * *") // 매일 23:59에 실행
	public void updateLastDayAssets() {
		try {
			rankingService.updateLastDayAssets();
		} catch (Exception e) {
			log.error("어제 총 자산 업데이트 중 오류 발생", e);
		}
	}

}
