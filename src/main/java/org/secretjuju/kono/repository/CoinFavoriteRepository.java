package org.secretjuju.kono.repository;

import java.util.List;

import org.secretjuju.kono.entity.CoinFavorite;
import org.secretjuju.kono.entity.CoinInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinFavoriteRepository extends JpaRepository<CoinFavorite, Integer> {

	// 특정 사용자의 모든 관심 코인 조회
	@Query("SELECT cf.coinInfo FROM CoinFavorite cf WHERE cf.user.id = :userId")
	List<CoinInfo> findAllCoinInfosByUserId(@Param("userId") Integer userId);

	// 특정 사용자의 특정 코인 관심 정보 삭제
	void deleteByUserIdAndCoinInfoId(Integer userId, Integer coinInfoId);

	// 특정 사용자의 특정 코인 관심 여부 확인
	boolean existsByUserIdAndCoinInfoId(Integer userId, Integer coinInfoId);
}