package org.secretjuju.kono.repository;

import java.util.List;

import org.secretjuju.kono.entity.CoinTransaction;
import org.secretjuju.kono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinTransactionRepository extends JpaRepository<CoinTransaction, Integer> {
	// 특정 사용자의 거래 내역을 최신순으로 조회
	List<CoinTransaction> findByUserOrderByCreatedAtDesc(User user);
}
