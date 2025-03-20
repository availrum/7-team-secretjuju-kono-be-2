package org.secretjuju.kono.repository;

import java.util.List;

import org.secretjuju.kono.entity.CoinTransaction;
import org.secretjuju.kono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinTransactionRepository extends JpaRepository<CoinTransaction, Integer> {

	List<CoinTransaction> findByUserOrderByCreatedAtDesc(User user);
}
