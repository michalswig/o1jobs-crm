package com.o1jobs.crm.agency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.o1jobs.crm.agency.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AssignmentResponse(
        Long id,
        Long clientId,
        Long careRecipientId,
        LocalDate startDate,
        String city,
        String streetAddress,
        BigDecimal salaryMonthlyNet,
        @JsonInclude(JsonInclude.Include.NON_NULL) BigDecimal contractValue,
        LanguageLevel languageLevel,
        String requirements,
        AssignmentStatus status,
        AssignmentCloseReason closeReason,
        String closeNotes,
        Long caregiverId,
        AccommodationType accommodationType,
        boolean ownBathroom,
        boolean ownRoom
) {
}