package org.secretjuju.kono.repository;

import java.util.Optional;

import org.secretjuju.kono.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByKakaoId(Long kakaoId);
}