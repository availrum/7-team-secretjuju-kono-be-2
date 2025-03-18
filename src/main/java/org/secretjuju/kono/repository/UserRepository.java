package org.secretjuju.kono.repository;

import java.util.Optional;

import org.secretjuju.kono.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findById(Integer id);
}