package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.*;
import com.o1jobs.crm.agency.dto.AssignmentMapper;
import com.o1jobs.crm.agency.dto.AssignmentRequest;
import com.o1jobs.crm.agency.dto.AssignmentResponse;
import com.o1jobs.crm.agency.repository.AssignmentRepository;
import com.o1jobs.crm.exception.NoSuchAssignmentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper;
    private final ClientService clientService;
    private final CaregiverService caregiverService;
    private final CareRecipientService careRecipientService;

    @Transactional(readOnly = true)
    public AssignmentResponse getById(Long id) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(
                () -> new NoSuchAssignmentException("No assignment with id " + id)
        );
        return assignmentMapper.toAssignmentResponse(assignment);
    }

    public AssignmentResponse create(AssignmentRequest request) {

        Client client = clientService.getEntityById(request.clientId());
        CareRecipient careRecipient = careRecipientService.getEntityById(request.careRecipientId());
        Caregiver caregiver = null;
        if (request.caregiverId() != null) {
            caregiver = caregiverService.getEntityById(request.caregiverId());
        }
        Assignment assignment = new Assignment(
                client,
                careRecipient,
                request.startDate(),
                request.city(),
                request.streetAddress(),
                request.salaryMonthlyNet(),
                request.languageLevel(),
                request.requirements(),
                request.status(),
                request.closeReason(),
                request.closeNotes(),
                caregiver
        );
        assignmentRepository.save(assignment);
        return assignmentMapper.toAssignmentResponse(assignment);
    }

    public AssignmentResponse update(Long id, AssignmentRequest request) {
        Assignment assignmentToUpdate = assignmentRepository.findById(id).orElseThrow(
                () -> new NoSuchAssignmentException("No assignmentToUpdate with id " + id)
        );

        if(request.caregiverId() != null) {
            assignmentToUpdate.assignCaregiver(caregiverService.getEntityById(request.caregiverId()));
        }

        assignmentMapper.updateAssignmentResponse(request, assignmentToUpdate);
        return assignmentMapper.toAssignmentResponse(assignmentToUpdate);
    }

    public void close(Long id, AssignmentCloseReason reason, String notes) {
        Assignment assignment = assignmentRepository.findById(id).orElseThrow(
                () -> new NoSuchAssignmentException("No assignment with id " + id)
        );
        assignment.close(reason, notes);
    }

}
