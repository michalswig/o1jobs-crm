package com.o1jobs.crm.agency.controller;

import com.o1jobs.crm.agency.domain.AssignmentDocument;
import com.o1jobs.crm.agency.dto.AssignmentDetailResponse;
import com.o1jobs.crm.agency.dto.AssignmentDocumentResponse;
import com.o1jobs.crm.agency.dto.AssignmentRequest;
import com.o1jobs.crm.agency.dto.AssignmentResponse;
import com.o1jobs.crm.agency.dto.CloseAssignmentRequest;
import com.o1jobs.crm.agency.service.AssignmentDocumentService;
import com.o1jobs.crm.agency.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;
    private final AssignmentDocumentService assignmentDocumentService;

    @GetMapping("/{id}/details")
    public ResponseEntity<AssignmentDetailResponse> getDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(assignmentService.getDetailById(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssignmentResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(assignmentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<AssignmentResponse> create(@Validated @RequestBody AssignmentRequest request) {
        AssignmentResponse assignmentResponse = assignmentService.create(request);
        URI uri = URI.create("api/v1/assignments" + assignmentResponse.id());
        return ResponseEntity.created(uri).body(assignmentResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssignmentResponse> update(@PathVariable Long id, @Validated @RequestBody AssignmentRequest request) {
        AssignmentResponse assignmentResponse = assignmentService.update(id, request);
        return ResponseEntity.ok(assignmentResponse);
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<Void> close(@Validated @RequestBody CloseAssignmentRequest request, @PathVariable Long id) {
        assignmentService.close(id, request.reason(), request.notes());
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<Page<AssignmentResponse>> getAll(
            @PageableDefault(size = 20, sort = "startDate") Pageable pageable,
            @RequestParam(required = false) Long clientId) {
        return ResponseEntity.ok(assignmentService.getAll(pageable, clientId));
    }

    @PostMapping("/{id}/document")
    public ResponseEntity<AssignmentDocumentResponse> uploadDocument(
            @PathVariable Long id, @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(assignmentDocumentService.upload(id, file));
    }

    @GetMapping("/{id}/document")
    public ResponseEntity<AssignmentDocumentResponse> getDocumentMetadata(@PathVariable Long id) {
        return ResponseEntity.ok(assignmentDocumentService.getMetadataByAssignmentId(id));
    }

    @GetMapping("/{id}/document/download")
    public ResponseEntity<byte[]> downloadDocument(@PathVariable Long id) {
        AssignmentDocument document = assignmentDocumentService.getDocumentOrThrow(id);
        byte[] content = assignmentDocumentService.downloadContent(id);
        ContentDisposition contentDisposition = ContentDisposition.attachment()
                .filename(document.getFileName(), StandardCharsets.UTF_8)
                .build();
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition.toString())
                .body(content);
    }

    @DeleteMapping("/{id}/document")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        assignmentDocumentService.delete(id);
        return ResponseEntity.noContent().build();
    }

}