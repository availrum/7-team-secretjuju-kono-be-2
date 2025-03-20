package org.secretjuju.kono.repository;

import java.util.List;
import java.util.Optional;

import org.secretjuju.kono.entity.CoinFavorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CoinFavoriteRepository extends JpaRepository<CoinFavorite, Integer> {

	// 특정 사용자의 모든 관심 코인 조회
	List<CoinFavorite> findAllByUserId(Long kakaoId);

	// 특정 사용자의 특정 코인 관심 여부 조회
	Optional<CoinFavorite> findByUserIdAndCoinInfoId(Integer userId, Integer coinInfoId);

	// 특정 코인에 대한 모든 관심 정보 조회
	List<CoinFavorite> findAllByCoinInfoId(Integer coinInfoId);

	// 특정 사용자의 특정 코인 관심 정보 삭제
	void deleteByUserIdAndCoinInfoId(Integer userId, Integer coinInfoId);

	// 특정 사용자의 관심 코인 수 조회
	long countByUserId(Integer userId);

	// 특정 코인의 관심 등록 수 조회
	long countByCoinInfoId(Integer coinInfoId);

	// 특정 사용자의 특정 코인 관심 여부 확인
	boolean existsByUserIdAndCoinInfoId(Integer userId, Integer coinInfoId);
}