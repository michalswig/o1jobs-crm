package com.o1jobs.crm.identity.dto;

import com.o1jobs.crm.identity.domain.UserRole;

import java.time.Instant;

public record UserResponse(Long id, String username, UserRole role, boolean active, Instant createdAt) {
}