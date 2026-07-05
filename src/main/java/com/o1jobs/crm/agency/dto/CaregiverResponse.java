package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.Gender;
import com.o1jobs.crm.agency.domain.Nationality;

import java.time.LocalDate;

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
        String recruiterNotes) {
}