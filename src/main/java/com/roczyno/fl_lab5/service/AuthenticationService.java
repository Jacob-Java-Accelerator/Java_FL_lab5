package com.roczyno.fl_lab5.service;

import com.roczyno.fl_lab5.dto.LoginRequest;
import com.roczyno.fl_lab5.dto.LoginResponse;
import com.roczyno.fl_lab5.dto.RegistrationRequest;
import com.roczyno.fl_lab5.model.User;
import com.roczyno.fl_lab5.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final RoleService roleService;

	public String register(RegistrationRequest req){
		User isEmailExist=userRepository.findByEmail(req.email());
		if(isEmailExist!=null){
			throw new RuntimeException("User with this email already exists");
		}
		User isUserNameExist=userRepository.findByUsername(req.username());
		if(isUserNameExist!=null){
			throw new RuntimeException("User with this username already exists");
		}
		User user=User.builder()
				.username(req.username())
				.email(req.email())
				.password(passwordEncoder.encode(req.password()))
				.roles(Set.of(roleService.getUserRole()))
				.createdAt(LocalDateTime.now())
				.build();
		userRepository.save(user);
		return "User registered successfully";
	}


	public LoginResponse login(LoginRequest req){
		Authentication authentication=authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(req.email(),req.password()));
		if(!authentication.isAuthenticated()){
			throw new BadCredentialsException("Bad credentials");
		}
		User user= userRepository.findByEmail(req.email());
		String jwt=jwtService.generateToken(user);
		return new LoginResponse(jwt,"Login successful");
	}
}
