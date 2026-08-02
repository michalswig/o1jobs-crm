package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.CareCapability;
import com.o1jobs.crm.agency.domain.DementiaLevel;
import com.o1jobs.crm.agency.domain.Gender;
import com.o1jobs.crm.agency.domain.Nationality;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Set;

public record CaregiverRequest(
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotNull Gender gender,
        @NotNull LocalDate birthDate,
        @NotNull Integer weightKg,
        @NotNull Integer heightCm,
        @NotBlank String phone,
        @NotBlank String email,
        @NotNull Nationality nationality,
        @NotNull LocalDate careerStartDate,
        boolean hasDriverLicense,
        boolean smoker,
        String medicalQualificationNotes,
        String recruiterNotes,
        DementiaLevel dementiaExperience,
        Set<CareCapability> capabilities) {
}