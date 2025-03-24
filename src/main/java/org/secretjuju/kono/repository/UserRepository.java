package org.secretjuju.kono.repository;

import java.util.Optional;

import org.secretjuju.kono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findById(Integer id);
	Optional<User> findByKakaoId(Long kakaoId);
	Optional<User> findByNickname(String nickname);
	void deleteByKakaoId(Long kakaoId);
}