package org.secretjuju.kono.repository;

import java.util.Optional;

import org.secretjuju.kono.entity.CoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoinRepository extends JpaRepository<CoinInfo, String> {
	Optional<CoinInfo> findByTicker(String ticker);
}