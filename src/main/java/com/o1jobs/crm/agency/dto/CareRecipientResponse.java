package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.DementiaLevel;
import com.o1jobs.crm.agency.domain.Gender;
import com.o1jobs.crm.agency.domain.MobilityLevel;
import com.o1jobs.crm.agency.domain.TransferType;

import java.time.LocalDate;

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
        boolean hasMs,
        boolean hasAlzheimer,
        boolean hasParkinson,
        String diseasesNotes,
        boolean isSmoker,
        boolean hasPets,
        String petsNotes,
        boolean needsTransfer,
        TransferType transferType,
        String liftingAidsNotes,
        boolean hasCatheter,
        boolean hasStoma,
        boolean useDiapers,
        String medicalNotes,
        Long clientId
) {
}
