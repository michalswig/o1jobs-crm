package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.*;
import com.o1jobs.crm.agency.dto.AssignmentDetailResponse;
import com.o1jobs.crm.agency.dto.AssignmentMapper;
import com.o1jobs.crm.agency.dto.AssignmentRequest;
import com.o1jobs.crm.agency.dto.AssignmentResponse;
import com.o1jobs.crm.agency.repository.AssignmentRepository;
import com.o1jobs.crm.agency.specification.AssignmentSpecifications;
import com.o1jobs.crm.exception.NoSuchAssignmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentService {

    // Role widzące wartość kontraktu (Auftragswert) w odpowiedziach API.
    // RECRUITER celowo pominięty - nie zajmuje się stroną finansową zleceń.
    private static final Set<String> CONTRACT_VALUE_VISIBLE_ROLES = Set.of("ADMIN", "MANAGER", "PARTNER");

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;
    private final ClientService clientService;
    private final CaregiverService caregiverService;
    private final CareRecipientService careRecipientService;

    @Transactional(readOnly = true)
    public AssignmentDetailResponse getDetailById(Long id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(
                () -> new NoSuchAssignmentException("No assignment with id " + id)
        );
        return maskContractValue(assignmentMapper.toAssignmentDetailResponse(assignment));
    }

    @Transactional(readOnly = true)
    public Page<AssignmentResponse> getAll(Pageable pageable, Long clientId) {
        Specification<Assignment> spec = Specification
                .where(AssignmentSpecifications.notDeleted())
                .and(AssignmentSpecifications.byClientId(clientId));
        return assignmentRepository.findAll(spec, pageable)
                .map(assignmentMapper::toAssignmentResponse)
                .map(this::maskContractValue);
    }

    @Transactional(readOnly = true)
    public AssignmentResponse getById(Long id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(
                () -> new NoSuchAssignmentException("No assignment with id " + id)
        );
        return maskContractValue(assignmentMapper.toAssignmentResponse(assignment));
    }

    public AssignmentResponse create(AssignmentRequest request) {
        Client client = clientService.getEntityById(request.clientId());
        CareRecipient careRecipient = careRecipientService.getEntityById(request.careRecipientId());
        Caregiver caregiver = null;
        if (request.caregiverId() != null) {
            caregiver = caregiverService.getEntityById(request.caregiverId());
        }
        Accommodation accommodation = new Accommodation(
                request.accommodationType(), request.ownBathroom(), request.ownRoom()
        );
        Assignment assignment = new Assignment(
                client,
                careRecipient,
                request.startDate(),
                request.city(),
                request.streetAddress(),
                request.salaryMonthlyNet(),
                request.languageLevel(),
                request.requirements(),
                caregiver,
                accommodation
        );
        assignment.updateContractValue(request.contractValue());
        assignmentRepository.save(assignment);
        return maskContractValue(assignmentMapper.toAssignmentResponse(assignment));
    }

    public AssignmentResponse update(Long id, AssignmentRequest request) {
        Assignment assignmentToUpdate = assignmentRepository.findById(id).orElseThrow(
                () -> new NoSuchAssignmentException("No assignment with id " + id)
        );

        Accommodation accommodation = new Accommodation(
                request.accommodationType(), request.ownBathroom(), request.ownRoom()
        );
        assignmentToUpdate.updateDetails(
                request.startDate(), request.city(), request.streetAddress(),
                request.salaryMonthlyNet(), request.languageLevel(), request.requirements(), accommodation
        );
        assignmentToUpdate.updateContractValue(request.contractValue());

        if (request.caregiverId() != null) {
            assignmentToUpdate.assignCaregiver(caregiverService.getEntityById(request.caregiverId()));
        }

        return maskContractValue(assignmentMapper.toAssignmentResponse(assignmentToUpdate));
    }

    public void close(Long id, AssignmentCloseReason reason, String notes) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(
                () -> new NoSuchAssignmentException("No assignment with id " + id)
        );
        assignment.close(reason, notes);
    }

    private boolean canSeeContractValue() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return false;
        }
        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(CONTRACT_VALUE_VISIBLE_ROLES::contains);
    }

    private AssignmentResponse maskContractValue(AssignmentResponse response) {
        if (canSeeContractValue()) {
            return response;
        }
        return new AssignmentResponse(
                response.id(), response.clientId(), response.careRecipientId(), response.startDate(),
                response.city(), response.streetAddress(), response.salaryMonthlyNet(), null,
                response.languageLevel(), response.requirements(), response.status(), response.closeReason(),
                response.closeNotes(), response.caregiverId(), response.accommodationType(),
                response.ownBathroom(), response.ownRoom()
        );
    }

    private AssignmentDetailResponse maskContractValue(AssignmentDetailResponse response) {
        if (canSeeContractValue()) {
            return response;
        }
        return new AssignmentDetailResponse(
                response.id(), response.startDate(), response.city(), response.streetAddress(),
                response.salaryMonthlyNet(), null, response.languageLevel(), response.requirements(),
                response.status(), response.closeReason(), response.closeNotes(), response.client(),
                response.careRecipient(), response.caregiver(), response.accommodationType(),
                response.ownBathroom(), response.ownRoom()
        );
    }
}