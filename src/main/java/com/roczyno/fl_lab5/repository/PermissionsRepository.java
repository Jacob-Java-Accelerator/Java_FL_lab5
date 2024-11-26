package com.roczyno.fl_lab5.repository;

import com.roczyno.fl_lab5.model.Permissions;
import com.roczyno.fl_lab5.model.enums.AppPermissions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionsRepository extends JpaRepository<Permissions,Long> {
	Optional<Permissions> findByPermissionName(AppPermissions permissionEnum);
}
