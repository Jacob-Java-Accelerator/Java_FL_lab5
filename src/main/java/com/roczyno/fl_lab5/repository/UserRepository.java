package com.roczyno.fl_lab5.repository;

import com.roczyno.fl_lab5.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
	User findByEmail(String username);

	User findByUsername(String username);

	boolean existsByUsername(String user);
}
