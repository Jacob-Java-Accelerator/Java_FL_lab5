package com.roczyno.fl_lab5.service;

import com.roczyno.fl_lab5.model.Permissions;
import com.roczyno.fl_lab5.model.Role;
import com.roczyno.fl_lab5.model.User;
import com.roczyno.fl_lab5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final RoleService roleService;

	@Override
	@Transactional
	public void addRoleToUser(Long userId, Long roleId) {
		Role role = roleService.getRoleById(roleId);
		User user = getUser(userId);

		if (user.getRoles().contains(role)) {
			throw new IllegalArgumentException("Role already assigned to the user.");
		}

		user.getRoles().add(role);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void removeRole(Long userId, Long roleId) {
		Role role = roleService.getRoleById(roleId);
		User user = getUser(userId);

		if (!user.getRoles().contains(role)) {
			throw new IllegalArgumentException("Role is not assigned to the user.");
		}

		user.getRoles().remove(role);
		userRepository.save(user);
	}

	@Override
	@Transactional
	public void addPermissions(Long userId, Long permissionId) {
		Permissions permission = roleService.getPermissionById(permissionId);
		User user = getUser(userId);

		user.getRoles().forEach(role -> {
			if (!role.getPermissions().contains(permission)) {
				role.getPermissions().add(permission);
			}
		});

		userRepository.save(user);
	}

	@Override
	@Transactional
	public void removePermissions(Long userId) {
		User user = getUser(userId);

		user.getRoles().forEach(role -> role.getPermissions().clear());
		userRepository.save(user);
	}

	@Override
	public User getUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new IllegalArgumentException("User with ID " + userId + " not found."));
	}
}
