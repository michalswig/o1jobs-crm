package com.o1jobs.crm.agency.dto;

import com.o1jobs.crm.agency.domain.AssignmentCloseReason;
import jakarta.validation.constraints.NotNull;

public record AssignmentCloseRequest(
        @NotNull AssignmentCloseReason closeReason,
        String closeNotes
) {
}
