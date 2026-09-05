package com.o1jobs.crm.agency.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.o1jobs.crm.agency.domain.AccommodationType;
import com.o1jobs.crm.agency.domain.AssignmentCloseReason;
import com.o1jobs.crm.agency.domain.AssignmentStatus;
import com.o1jobs.crm.agency.domain.LanguageLevel;

import java.math.BigDecimal;
import java.time.LocalDate;

public record AssignmentDetailResponse(
        Long id,
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
        ClientResponse client,
        CareRecipientResponse careRecipient,
        CaregiverResponse caregiver,
        AccommodationType accommodationType,
        boolean ownBathroom,
        boolean ownRoom
) {
}