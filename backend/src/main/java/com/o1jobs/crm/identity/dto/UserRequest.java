package com.o1jobs.crm.identity.dto;

import com.o1jobs.crm.identity.domain.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UserRequest(
        @NotBlank String username,
        @NotNull UserRole role,
        @Size(min = 8, max = 32)
        String password) {
}
