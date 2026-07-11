package com.o1jobs.crm.agency.dto;

import jakarta.validation.constraints.NotBlank;

public record ClientRequest(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String phoneNumber,
        @NotBlank String country,
        @NotBlank String city,
        @NotBlank String postalCode,
        @NotBlank String streetAddress,
        String notes,
        Long intermediary_id) {
}