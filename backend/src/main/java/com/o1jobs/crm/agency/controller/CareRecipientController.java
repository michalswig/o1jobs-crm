package com.o1jobs.crm.agency.controller;

import com.o1jobs.crm.agency.dto.CareRecipientRequest;
import com.o1jobs.crm.agency.dto.CareRecipientResponse;
import com.o1jobs.crm.agency.service.CareRecipientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("api/v1/care-recipients")
@RequiredArgsConstructor
public class CareRecipientController {
    private final CareRecipientService careRecipientService;

    @GetMapping("/{id}")
    ResponseEntity<CareRecipientResponse> getById(@PathVariable Long id) {
        CareRecipientResponse careRecipientResponse = careRecipientService.getById(id);
        return ResponseEntity.ok(careRecipientResponse);
    }

    @PostMapping
    ResponseEntity<CareRecipientResponse> create(@Validated @RequestBody CareRecipientRequest careRecipientRequest) {
        CareRecipientResponse careRecipientResponse = careRecipientService.create(careRecipientRequest);
        URI uri = URI.create("/api/v1/carerecipients/" + careRecipientResponse.id());
        return ResponseEntity.created(uri).body(careRecipientResponse);
    }

    @PutMapping("/{id}")
    ResponseEntity<CareRecipientResponse> update(@Validated @RequestBody CareRecipientRequest careRecipientRequest, @PathVariable Long id) {
        CareRecipientResponse careRecipientResponse = careRecipientService.update(id, careRecipientRequest);
        return ResponseEntity.ok(careRecipientResponse);
    }

    @PatchMapping("/{id}/deactivate")
    ResponseEntity<Void> deactivate(@PathVariable Long id) {
        careRecipientService.deactivateCareRecipient(id);
        return ResponseEntity.noContent().build();
    }

}
