package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.AccommodationType;
import com.o1jobs.crm.agency.domain.LanguageLevel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AssignmentRequest(
        @NotNull Long clientId,
        @NotNull Long careRecipientId,
        @NotNull LocalDate startDate,
        @NotBlank String city,
        String streetAddress,
        @NotNull BigDecimal salaryMonthlyNet,
        BigDecimal contractValue,
        @NotNull LanguageLevel languageLevel,
        String requirements,
        Long caregiverId,
        @NotNull AccommodationType accommodationType,
        boolean ownBathroom,
        boolean ownRoom
) {
}