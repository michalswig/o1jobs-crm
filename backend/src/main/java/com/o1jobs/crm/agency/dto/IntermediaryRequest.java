package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.IntermediaryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record IntermediaryRequest(
        @NotNull IntermediaryType intermediaryType,
        @NotBlank String name,
        String country,
        String city,
        String postalCode,
        String streetAddress,
        @NotBlank String email,
        @NotBlank String phone,
        String notes
) {
}