package org.secretjuju.kono.repository;

import org.secretjuju.kono.entity.CoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface CoinRepository extends JpaRepository<CoinInfo, String> {
	Optional<CoinInfo> findByTicker(String ticker);
}
