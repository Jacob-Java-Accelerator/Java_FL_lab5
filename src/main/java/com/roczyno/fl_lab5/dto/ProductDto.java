package com.roczyno.fl_lab5.dto;

import java.time.LocalDateTime;

public record ProductDto(
		String name,
		String description,
		LocalDateTime createdAt
		) {
}
