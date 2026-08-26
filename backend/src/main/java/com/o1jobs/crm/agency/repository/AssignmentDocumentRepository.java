package com.o1jobs.crm.agency.repository;

import com.o1jobs.crm.agency.domain.AssignmentDocument;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssignmentDocumentRepository extends JpaRepository<AssignmentDocument, Long> {
    Optional<AssignmentDocument> findByAssignmentId(Long assignmentId);
}