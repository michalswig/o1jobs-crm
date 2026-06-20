package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AssignmentRequest(
        @NotNull Long clientId,
        @NotNull Long careRecipientId,
        @NotNull LocalDate startDate,
        @NotBlank String city,
        @NotBlank String streetAddress,
        @NotNull BigDecimal salaryMonthlyNet,
        @NotNull LanguageLevel languageLevel,
        String requirements,
        @NotNull AssignmentStatus status,
        AssignmentCloseReason closeReason,
        String closeNotes,
        Long caregiverId
) {
}
