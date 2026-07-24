package com.o1jobs.crm.agency.controller;

import com.o1jobs.crm.agency.dto.CaregiverRequest;
import com.o1jobs.crm.agency.dto.CaregiverResponse;
import com.o1jobs.crm.agency.service.CaregiverService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/caregivers")
@RequiredArgsConstructor
public class CaregiverController {
    private final CaregiverService caregiverService;

    @GetMapping
    ResponseEntity<Page<CaregiverResponse>> getAll(@PageableDefault(size = 20, sort = "lastName") Pageable pageable) {
        return ResponseEntity.ok(caregiverService.getAll(pageable));
    }

    @GetMapping("/{id}")
    ResponseEntity<CaregiverResponse> getById(@PathVariable long id) {
        return ResponseEntity.ok(caregiverService.getById(id));
    }

    @PostMapping()
    ResponseEntity<CaregiverResponse> create(@Validated @RequestBody CaregiverRequest caregiverRequest) {
        CaregiverResponse caregiverResponse = caregiverService.create(caregiverRequest);
        URI uri = URI.create("/api/v1/caregivers" + caregiverResponse.id());
        return ResponseEntity.created(uri).body(caregiverResponse);
    }

    @PutMapping("/{id}")
    ResponseEntity<CaregiverResponse> update(@PathVariable long id, @RequestBody CaregiverRequest caregiverRequest) {
        CaregiverResponse careGiverResponse = caregiverService.update(caregiverRequest, id);
        return ResponseEntity.ok(careGiverResponse);
    }

    @PatchMapping("/{id}/deactivate")
    ResponseEntity<Void> deactivate(@PathVariable long id) {
        caregiverService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

}
