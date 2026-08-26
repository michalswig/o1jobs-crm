package com.o1jobs.crm.agency.dto;

import java.time.Instant;

public record AssignmentDocumentResponse(
        Long id,
        String fileName,
        String contentType,
        long fileSize,
        Instant uploadedAt
) {
}