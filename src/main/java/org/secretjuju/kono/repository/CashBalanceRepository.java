package org.secretjuju.kono.repository;

import java.util.Optional;

import org.secretjuju.kono.entity.CashBalance;
import org.secretjuju.kono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CashBalanceRepository extends JpaRepository<CashBalance, Integer> {
	Optional<CashBalance> findByUser(User user);
}
