package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.AssignmentCloseReason;
import jakarta.validation.constraints.NotNull;

public record CloseAssignmentRequest(
        @NotNull AssignmentCloseReason reason,
        String notes
) {
}
