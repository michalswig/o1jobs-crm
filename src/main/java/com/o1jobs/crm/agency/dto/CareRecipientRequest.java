package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.*;

import java.time.LocalDate;

public record CareRecipientRequest(
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
        boolean smoker,
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
