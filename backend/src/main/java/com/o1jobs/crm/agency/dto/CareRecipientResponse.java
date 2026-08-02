package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.CareCapability;
import com.o1jobs.crm.agency.domain.DementiaLevel;
import com.o1jobs.crm.agency.domain.Gender;
import com.o1jobs.crm.agency.domain.MobilityLevel;

import java.time.LocalDate;
import java.util.Set;

public record CareRecipientResponse(
        Long id,
        String firstName,
        String lastName,
        LocalDate dateOfBirth,
        Integer heightCm,
        Integer weightKg,
        Gender gender,
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