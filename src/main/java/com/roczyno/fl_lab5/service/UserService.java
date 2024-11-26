package com.roczyno.fl_lab5.service;

import com.roczyno.fl_lab5.model.User;

public interface UserService {
	void addRoleToUser(Long userId,Long roleId);
	void removeRole(Long userId,Long roleId);
	void addPermissions(Long userId,Long permissionId);
	void removePermissions(Long userId);
	User getUser(Long userId);
}
