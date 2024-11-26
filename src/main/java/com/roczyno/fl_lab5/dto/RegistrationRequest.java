package com.roczyno.fl_lab5.dto;

public record RegistrationRequest(
		String email,
		String username,
		String password
) {
}
