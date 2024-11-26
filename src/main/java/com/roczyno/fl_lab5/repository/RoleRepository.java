package com.roczyno.fl_lab5.repository;

import com.roczyno.fl_lab5.model.Role;
import com.roczyno.fl_lab5.model.enums.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
	Optional<Role> findByRoleName(AppRole appRole);
}
