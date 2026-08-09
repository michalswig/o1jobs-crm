package com.o1jobs.crm.agency.dto;

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
        LanguageLevel languageLevel,
        String requirements,
        AssignmentStatus status,
        AssignmentCloseReason closeReason,
        String closeNotes,
        ClientResponse client,
        CareRecipientResponse careRecipient,
        CaregiverResponse caregiver
) {
}