package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.Assignment;
import com.o1jobs.crm.agency.domain.AssignmentDocument;
import com.o1jobs.crm.agency.dto.AssignmentDocumentResponse;
import com.o1jobs.crm.agency.repository.AssignmentDocumentRepository;
import com.o1jobs.crm.agency.repository.AssignmentRepository;
import com.o1jobs.crm.exception.InvalidFileTypeException;
import com.o1jobs.crm.exception.NoSuchAssignmentException;
import com.o1jobs.crm.exception.NoSuchDocumentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class AssignmentDocumentService {

    private static final String ALLOWED_CONTENT_TYPE = "application/pdf";

    private final AssignmentDocumentRepository documentRepository;
    private final AssignmentRepository assignmentRepository;
    private final DocumentStorageService documentStorageService;

    public AssignmentDocumentResponse upload(Long assignmentId, MultipartFile file) {
        if (file.isEmpty()) {
            throw new InvalidFileTypeException("Die hochgeladene Datei ist leer.");
        }
        if (!ALLOWED_CONTENT_TYPE.equals(file.getContentType())) {
            throw new InvalidFileTypeException("Es sind nur PDF-Dateien zulässig.");
        }

        Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(
                () -> new NoSuchAssignmentException("No assignment with id " + assignmentId)
        );

        // Nowy, unikalny klucz przy każdym uploadzie - stary plik w R2 (jeśli był) kasujemy
        // dopiero PO udanym uploadzie nowego, żeby nie zostać bez żadnej wersji w razie błędu.
        String storageKey = "assignments/" + assignmentId + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        documentStorageService.upload(storageKey, readBytes(file), file.getContentType());

        AssignmentDocument existing = documentRepository.findByAssignmentId(assignmentId).orElse(null);
        String previousStorageKey = existing != null ? existing.getStorageKey() : null;

        AssignmentDocument document;
        if (existing != null) {
            existing.replace(file.getOriginalFilename(), file.getContentType(), file.getSize(), storageKey);
            document = existing;
        } else {
            document = new AssignmentDocument(
                    assignment, file.getOriginalFilename(), file.getContentType(), file.getSize(), storageKey
            );
        }
        documentRepository.save(document);

        if (previousStorageKey != null) {
            documentStorageService.delete(previousStorageKey);
        }

        return toResponse(document);
    }

    @Transactional(readOnly = true)
    public AssignmentDocumentResponse getMetadataByAssignmentId(Long assignmentId) {
        return toResponse(getDocumentOrThrow(assignmentId));
    }

    @Transactional(readOnly = true)
    public byte[] downloadContent(Long assignmentId) {
        AssignmentDocument document = getDocumentOrThrow(assignmentId);
        return documentStorageService.download(document.getStorageKey());
    }

    @Transactional(readOnly = true)
    public AssignmentDocument getDocumentOrThrow(Long assignmentId) {
        return documentRepository.findByAssignmentId(assignmentId).orElseThrow(
                () -> new NoSuchDocumentException("No document for assignment with id " + assignmentId)
        );
    }

    public void delete(Long assignmentId) {
        AssignmentDocument document = getDocumentOrThrow(assignmentId);
        documentRepository.delete(document);
        documentStorageService.delete(document.getStorageKey());
    }

    private byte[] readBytes(MultipartFile file) {
        try {
            return file.getBytes();
        } catch (IOException e) {
            throw new UncheckedIOException("Die hochgeladene Datei konnte nicht gelesen werden.", e);
        }
    }

    private AssignmentDocumentResponse toResponse(AssignmentDocument document) {
        return new AssignmentDocumentResponse(
                document.getId(),
                document.getFileName(),
                document.getContentType(),
                document.getFileSize(),
                document.getUploadedAt()
        );
    }
}