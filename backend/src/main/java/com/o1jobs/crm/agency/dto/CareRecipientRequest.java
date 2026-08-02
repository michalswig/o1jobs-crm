package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.CareCapability;
import com.o1jobs.crm.agency.domain.DementiaLevel;
import com.o1jobs.crm.agency.domain.Gender;
import com.o1jobs.crm.agency.domain.MobilityLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CareRecipientRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull LocalDate dateOfBirth,
        @NotNull Integer heightCm,
        @NotNull Integer weightKg,
        @NotNull Gender gender,
        MobilityLevel mobilityLevel,
        DementiaLevel dementiaLevel,
        String diseasesNotes,
        boolean smoker,
        boolean hasPets,
        String petsNotes,
        String liftingAidsNotes,
        String medicalNotes,
        Set<CareCapability> requiredCapabilities,
        Long clientId
) {
}