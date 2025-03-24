package org.secretjuju.kono.repository;

import java.util.List;
import java.util.Optional;

import org.secretjuju.kono.entity.DailyRanking;
import org.secretjuju.kono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyRankingRepository extends JpaRepository<DailyRanking, Long> {
	Optional<DailyRanking> findByUser(User user);
	List<DailyRanking> findAllByOrderByProfitRateDesc();
	List<DailyRanking> findTop100ByOrderByDailyRankAsc();
}