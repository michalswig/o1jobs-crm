package com.o1jobs.crm.agency.controller;

import com.o1jobs.crm.agency.dto.AssignmentRequest;
import com.o1jobs.crm.agency.dto.AssignmentResponse;
import com.o1jobs.crm.agency.dto.CloseAssignmentRequest;
import com.o1jobs.crm.agency.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/assignments")
@RequiredArgsConstructor
public class AssignmentController {
    private final AssignmentService assignmentService;

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

}
