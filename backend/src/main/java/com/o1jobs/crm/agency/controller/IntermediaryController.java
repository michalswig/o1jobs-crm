package com.o1jobs.crm.agency.controller;

import com.o1jobs.crm.agency.dto.IntermediaryRequest;
import com.o1jobs.crm.agency.dto.IntermediaryResponse;
import com.o1jobs.crm.agency.service.IntermediaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/intermediaries")
@RequiredArgsConstructor
public class IntermediaryController {
    private final IntermediaryService intermediaryService;

    @GetMapping
    public ResponseEntity<Page<IntermediaryResponse>> getAll(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(intermediaryService.getAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntermediaryResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(intermediaryService.getById(id));
    }

    @PostMapping
    public ResponseEntity<IntermediaryResponse> create(@Validated @RequestBody IntermediaryRequest request) {
        IntermediaryResponse response = intermediaryService.create(request);
        URI uri = URI.create("/api/v1/intermediaries/" + response.id());
        return ResponseEntity.created(uri).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntermediaryResponse> update(@PathVariable Long id, @Validated @RequestBody IntermediaryRequest request) {
        return ResponseEntity.ok(intermediaryService.update(id, request));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable Long id) {
        intermediaryService.deactivate(id);
        return ResponseEntity.noContent().build();
    }
}