package com.o1jobs.crm.agency.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public record DashboardResponse(
        long openAssignmentsCount,
        long careRecipientsWithoutAssignmentCount,
        BigDecimal totalMonthlyContractValue,
        Map<String, Long> statusBreakdown,
        Map<String, Long> closeReasonBreakdown,
        Map<String, Long> nationalityBreakdown,
        Map<String, Long> dementiaExperienceBreakdown,
        long openAssignmentsMissingDocumentCount,
        List<MonthlyCount> monthlyTrend,
        List<CaregiverAvailability> caregiverAvailability,
        List<PartnerSummary> partnerBreakdown,
        List<AssignmentSummary> assignmentsStartingThisMonth
) {
    public record MonthlyCount(String month, long count) {
    }

    public record CaregiverAvailability(Long caregiverId, String fullName, boolean assigned) {
    }

    public record PartnerSummary(Long partnerId, String partnerName, List<String> clientNames, BigDecimal totalContractValue) {
    }

    public record AssignmentSummary(Long assignmentId, String city, LocalDate startDate, String clientName, BigDecimal contractValue) {
    }
}