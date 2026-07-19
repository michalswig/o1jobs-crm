package com.o1jobs.crm.agency.controller;

import com.o1jobs.crm.agency.dto.ClientRequest;
import com.o1jobs.crm.agency.dto.ClientResponse;
import com.o1jobs.crm.agency.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping
    public ResponseEntity<ClientResponse> create(@Validated @RequestBody ClientRequest clientRequest) {
        ClientResponse clientResponse = clientService.create(clientRequest);
        URI uri = URI.create("api/v1/clients" + clientResponse.id());
        return ResponseEntity.created(uri).body(clientResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> get(@PathVariable long id) {
        return ResponseEntity.ok(clientService.getById(id));
    }

    @GetMapping
    public ResponseEntity<Page<ClientResponse>> getAll(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return ResponseEntity.ok(clientService.getAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClientResponse> update(@Validated @RequestBody ClientRequest clientRequest, @PathVariable Long id) {
        ClientResponse updatedClient = clientService.update(clientRequest, id);
        return ResponseEntity.ok(updatedClient);
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<Void> deactivate(@PathVariable long id) {
        clientService.deactivate(id);
        return ResponseEntity.noContent().build();
    }

}
