package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.IntermediaryType;

public record IntermediaryResponse(
        Long id,
        IntermediaryType intermediaryType,
        String name,
        String country,
        String city,
        String postalCode,
        String streetAddress,
        String email,
        String phone,
        String notes
) {
}