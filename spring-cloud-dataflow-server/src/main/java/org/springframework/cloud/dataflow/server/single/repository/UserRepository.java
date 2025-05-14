package org.springframework.cloud.dataflow.server.single.repository;

import org.springframework.cloud.dataflow.server.single.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findUserByUsernameAndPassword(String username, String password);
}
