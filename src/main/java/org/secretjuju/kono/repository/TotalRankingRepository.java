package org.secretjuju.kono.repository;

import java.util.List;
import java.util.Optional;

import org.secretjuju.kono.entity.TotalRanking;
import org.secretjuju.kono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TotalRankingRepository extends JpaRepository<TotalRanking, Integer> {
	Optional<TotalRanking> findByUser(User user);
	List<TotalRanking> findAllByOrderByCurrentTotalAssetsDesc();
}