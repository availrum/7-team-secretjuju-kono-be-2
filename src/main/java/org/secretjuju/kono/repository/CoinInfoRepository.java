package org.secretjuju.kono.repository;

import java.util.Optional;

import org.secretjuju.kono.entity.CoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinInfoRepository extends JpaRepository<CoinInfo, Integer> {
    Optional<CoinInfo> findByTicker(String ticker);
} 