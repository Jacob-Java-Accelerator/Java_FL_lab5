package com.roczyno.fl_lab5.controller;

import com.roczyno.fl_lab5.dto.LoginRequest;
import com.roczyno.fl_lab5.dto.LoginResponse;
import com.roczyno.fl_lab5.dto.RegistrationRequest;
import com.roczyno.fl_lab5.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
	private final AuthenticationService authenticationService;

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegistrationRequest req){
		return ResponseEntity.ok(authenticationService.register(req));
	}
	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req){
		return ResponseEntity.ok(authenticationService.login(req));
	}
}
