package com.roczyno.fl_lab5.service;

import com.roczyno.fl_lab5.model.Role;
import com.roczyno.fl_lab5.model.User;
import com.roczyno.fl_lab5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

	private static final Logger logger = LoggerFactory.getLogger(DataInitializer.class);

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;

	@Value("${spring.application.username:user}")
	private String defaultUserUsername;

	@Value("${spring.application.user_email:user@gmail.com}")
	private String defaultUserEmail;

	@Value("${spring.application.admin_username:admin}")
	private String defaultAdminUsername;

	@Value("${spring.application.admin_email:admin@gmail.com}")
	private String defaultAdminEmail;

	@Value("${spring.application.user_password:123456}")
	private String defaultUserPassword;

	@Value("${spring.application.admin_password:123456}")
	private String defaultAdminPassword;

	@Bean
	public CommandLineRunner initData() {
		return args -> {
			try {
				createDefaultUser(defaultUserUsername, defaultUserEmail, defaultUserPassword, roleService.getUserRole());
				createDefaultUser(defaultAdminUsername, defaultAdminEmail, defaultAdminPassword, roleService.getAdminRole());
			} catch (Exception e) {
				logger.error("Error during data initialization: ", e);
			}
		};
	}

	/**
	 * Helper method to create a default user or admin if they do not already exist.
	 *
	 * @param username the username of the user.
	 * @param email    the email of the user.
	 * @param password the raw password of the user.
	 * @param role     the role to assign to the user.
	 */
	private void createDefaultUser(String username, String email, String password, Role role) {
		if (!userRepository.existsByUsername(username)) {
			User user = User.builder()
					.username(username)
					.email(email)
					.password(passwordEncoder.encode(password))
					.createdAt(LocalDateTime.now())
					.roles(Set.of(role))
					.build();
			userRepository.save(user);
			logger.info("Initialized default user: {}", username);
		} else {
			logger.info("Default user '{}' already exists. Skipping initialization.", username);
		}
	}
}
