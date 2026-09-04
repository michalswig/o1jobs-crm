package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.CareCapability;
import com.o1jobs.crm.agency.domain.DementiaLevel;
import com.o1jobs.crm.agency.domain.Gender;
import com.o1jobs.crm.agency.domain.Nationality;

import java.time.LocalDate;
import java.util.Set;

public record CaregiverResponse(
        Long id,
        String firstName,
        String lastName,
        Gender gender,
        LocalDate birthDate,
        Integer weightKg,
        Integer heightCm,
        String phone,
        String email,
        Nationality nationality,
        LocalDate careerStartDate,
        boolean hasDriverLicense,
        boolean smoker,
        String medicalQualificationNotes,
        String recruiterNotes,
        DementiaLevel dementiaExperience,
        Set<CareCapability> capabilities,
        boolean hasPhoto) {
}