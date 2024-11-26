package com.roczyno.fl_lab5.service;

import com.roczyno.fl_lab5.model.Permissions;
import com.roczyno.fl_lab5.model.Role;
import com.roczyno.fl_lab5.model.enums.AppPermissions;
import com.roczyno.fl_lab5.model.enums.AppRole;
import com.roczyno.fl_lab5.repository.PermissionsRepository;
import com.roczyno.fl_lab5.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoleService {
	private final RoleRepository roleRepository;
	private final PermissionsRepository permissionsRepository;

	/**
	 * Retrieves all available permissions, creating them if they do not already exist.
	 *
	 * @return a set of all permissions.
	 */
	private Set<Permissions> getPermissions() {
		return Set.of(AppPermissions.values()).stream()
				.map(permissionEnum -> permissionsRepository.findByPermissionName(permissionEnum)
						.orElseGet(() -> permissionsRepository.save(
								Permissions.builder().permissionName(permissionEnum).build())))
				.collect(Collectors.toSet());
	}

	/**
	 * Retrieves the specified role by name, creating it with the given permissions if it does not exist.
	 *
	 * @param roleName    the name of the role (AppRole).
	 * @param permissions the set of permissions to assign if the role needs to be created.
	 * @return the Role object.
	 */
	private Role getOrCreateRole(AppRole roleName, Set<AppPermissions> permissions) {
		return roleRepository.findByRoleName(roleName)
				.orElseGet(() -> {
					Role role = new Role();
					role.setRoleName(roleName);
					role.setPermissions(getPermissions().stream()
							.filter(p -> permissions.contains(p.getPermissionName()))
							.collect(Collectors.toSet()));
					return roleRepository.save(role);
				});
	}

	/**
	 * Retrieves the ROLE_USER role, creating it with READ permission if it does not exist.
	 *
	 * @return the ROLE_USER Role object.
	 */
	public Role getUserRole() {
		return getOrCreateRole(AppRole.ROLE_USER, Set.of(AppPermissions.READ));
	}

	/**
	 * Retrieves the ROLE_ADMIN role, creating it with all permissions if it does not exist.
	 *
	 * @return the ROLE_ADMIN Role object.
	 */
	public Role getAdminRole() {
		return getOrCreateRole(AppRole.ROLE_ADMIN, Set.of(AppPermissions.values()));
	}

	public Role getRoleById(Long roleId) {
		return roleRepository.findById(roleId).orElseThrow();
	}

	public Permissions getPermissionById(Long permissionId) {
		return permissionsRepository.findById(permissionId).orElseThrow();
	}
}
