package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.*;
import com.o1jobs.crm.agency.dto.DashboardResponse;
import com.o1jobs.crm.agency.repository.AssignmentDocumentRepository;
import com.o1jobs.crm.agency.repository.AssignmentRepository;
import com.o1jobs.crm.agency.repository.CaregiverRepository;
import com.o1jobs.crm.agency.repository.CareRecipientRepository;
import com.o1jobs.crm.agency.repository.ClientRepository;
import com.o1jobs.crm.agency.specification.AssignmentSpecifications;
import com.o1jobs.crm.agency.specification.ClientSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {

    private final AssignmentRepository assignmentRepository;
    private final CaregiverRepository caregiverRepository;
    private final CareRecipientRepository careRecipientRepository;
    private final AssignmentDocumentRepository assignmentDocumentRepository;
    private final ClientRepository clientRepository;

    public DashboardResponse getDashboard() {
        List<Assignment> allAssignments = assignmentRepository.findAll(AssignmentSpecifications.notDeleted());
        List<Assignment> openAssignments = allAssignments.stream()
                .filter(a -> a.getStatus() == AssignmentStatus.OPEN)
                .toList();

        long openCount = openAssignments.size();
        long closedCount = allAssignments.size() - openCount;

        Set<Long> careRecipientIdsWithOpenAssignment = openAssignments.stream()
                .map(a -> a.getCareRecipient().getId())
                .collect(Collectors.toSet());
        long totalCareRecipients = careRecipientRepository.count();
        long careRecipientsWithoutAssignment = totalCareRecipients - careRecipientIdsWithOpenAssignment.size();

        Set<Long> assignedCaregiverIds = openAssignments.stream()
                .map(Assignment::getCaregiver)
                .filter(Objects::nonNull)
                .map(Caregiver::getId)
                .collect(Collectors.toSet());

        // Suma wartości kontraktów otwartych zleceń - to co płacą klienci łącznie,
        // NIE marża. Marża wymaga pełnego modelu kosztów (P&L), którego jeszcze nie mamy -
        // dopóki go nie zbudujemy, pokazujemy tylko wolumen przychodu, bez odejmowania kosztów.
        BigDecimal totalContractValue = openAssignments.stream()
                .filter(a -> a.getContractValue() != null)
                .map(Assignment::getContractValue)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Long> statusBreakdown = new LinkedHashMap<>();
        statusBreakdown.put("OPEN", openCount);
        statusBreakdown.put("CLOSED", closedCount);

        Map<String, Long> closeReasonBreakdown = allAssignments.stream()
                .filter(a -> a.getStatus() == AssignmentStatus.CLOSED && a.getCloseReason() != null)
                .collect(Collectors.groupingBy(a -> a.getCloseReason().name(), Collectors.counting()));

        List<Caregiver> allCaregivers = caregiverRepository.findAll();
        Map<String, Long> nationalityBreakdown = allCaregivers.stream()
                .collect(Collectors.groupingBy(c -> c.getNationality().name(), Collectors.counting()));
        Map<String, Long> dementiaExperienceBreakdown = allCaregivers.stream()
                .filter(c -> c.getDementiaExperience() != null)
                .collect(Collectors.groupingBy(c -> c.getDementiaExperience().name(), Collectors.counting()));

        long missingDocumentCount = openAssignments.stream()
                .filter(a -> assignmentDocumentRepository.findByAssignmentId(a.getId()).isEmpty())
                .count();

        List<DashboardResponse.MonthlyCount> trend = new ArrayList<>();
        YearMonth current = YearMonth.now();
        for (int i = 5; i >= 0; i--) {
            YearMonth ym = current.minusMonths(i);
            long count = allAssignments.stream()
                    .filter(a -> YearMonth.from(a.getStartDate()).equals(ym))
                    .count();
            trend.add(new DashboardResponse.MonthlyCount(ym.toString(), count));
        }

        // Lista dostępności opiekunek - zajęte pierwsze, potem wolne, w obu grupach wg nazwiska
        List<DashboardResponse.CaregiverAvailability> caregiverAvailability = allCaregivers.stream()
                .map(c -> new DashboardResponse.CaregiverAvailability(
                        c.getId(),
                        c.getFirstName() + " " + c.getLastName(),
                        assignedCaregiverIds.contains(c.getId())
                ))
                .sorted(Comparator
                        .comparing(DashboardResponse.CaregiverAvailability::assigned).reversed()
                        .thenComparing(DashboardResponse.CaregiverAvailability::fullName))
                .toList();

        // Rozliczenie na partnera: suma wartości kontraktów otwartych zleceń wśród
        // rodzin przypisanych do danego partnera.
        Map<Long, BigDecimal> clientContractValueMap = openAssignments.stream()
                .filter(a -> a.getContractValue() != null)
                .collect(Collectors.groupingBy(
                        a -> a.getClient().getId(),
                        Collectors.reducing(BigDecimal.ZERO, Assignment::getContractValue, BigDecimal::add)
                ));

        List<Client> allClients = clientRepository.findAll(ClientSpecifications.notDeleted());
        Map<Intermediary, List<Client>> clientsByPartner = allClients.stream()
                .filter(c -> c.getIntermediary() != null)
                .collect(Collectors.groupingBy(Client::getIntermediary));

        List<DashboardResponse.PartnerSummary> partnerBreakdown = clientsByPartner.entrySet().stream()
                .map(entry -> {
                    Intermediary partner = entry.getKey();
                    List<Client> clients = entry.getValue();
                    List<String> clientNames = clients.stream().map(Client::getName).toList();
                    BigDecimal total = clients.stream()
                            .map(c -> clientContractValueMap.getOrDefault(c.getId(), BigDecimal.ZERO))
                            .reduce(BigDecimal.ZERO, BigDecimal::add);
                    return new DashboardResponse.PartnerSummary(partner.getId(), partner.getName(), clientNames, total);
                })
                .sorted(Comparator.comparing(DashboardResponse.PartnerSummary::totalContractValue).reversed())
                .toList();

        // Zlecenia zaczynające się w bieżącym miesiącu kalendarzowym
        YearMonth thisMonth = YearMonth.now();
        List<DashboardResponse.AssignmentSummary> startingThisMonth = allAssignments.stream()
                .filter(a -> YearMonth.from(a.getStartDate()).equals(thisMonth))
                .map(a -> new DashboardResponse.AssignmentSummary(
                        a.getId(), a.getCity(), a.getStartDate(), a.getClient().getName(), a.getContractValue()
                ))
                .sorted(Comparator.comparing(DashboardResponse.AssignmentSummary::startDate))
                .toList();

        return new DashboardResponse(
                openCount,
                careRecipientsWithoutAssignment,
                totalContractValue,
                statusBreakdown,
                closeReasonBreakdown,
                nationalityBreakdown,
                dementiaExperienceBreakdown,
                missingDocumentCount,
                trend,
                caregiverAvailability,
                partnerBreakdown,
                startingThisMonth
        );
    }
}