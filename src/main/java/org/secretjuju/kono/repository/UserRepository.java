package org.secretjuju.kono.repository;

import java.util.List;
import java.util.Optional;

import org.secretjuju.kono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findById(Integer id);
	Optional<User> findByKakaoId(Long kakaoId);
	void deleteByKakaoId(Long kakaoId);
	List<User> findAll();
}