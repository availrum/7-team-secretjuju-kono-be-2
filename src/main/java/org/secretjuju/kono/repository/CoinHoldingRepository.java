package org.secretjuju.kono.repository;

import java.util.List;

import org.secretjuju.kono.entity.CoinHolding;
import org.secretjuju.kono.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface CoinHoldingRepository extends CrudRepository<CoinHolding, Long> {
	List<CoinHolding> findByUser(User user);
}
