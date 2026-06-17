package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.CareRecipient;
import com.o1jobs.crm.agency.domain.Client;
import com.o1jobs.crm.agency.dto.CareRecipientMapper;
import com.o1jobs.crm.agency.dto.CareRecipientRequest;
import com.o1jobs.crm.agency.dto.CareRecipientResponse;
import com.o1jobs.crm.agency.repository.CareRecipientRepository;
import com.o1jobs.crm.agency.repository.ClientRepository;
import com.o1jobs.crm.exception.NoSuchCareRecipientException;
import com.o1jobs.crm.exception.NoSuchClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CareRecipientService {
    private final CareRecipientRepository careRecipientRepository;
    private final ClientRepository clientRepository;
    private final CareRecipientMapper careRecipientMapper;

    public CareRecipientResponse getById(Long id) {
        CareRecipient careRecipient = careRecipientRepository.findById(id).orElseThrow(
                () -> new NoSuchCareRecipientException("CareRecipient with id " + id + " not found")
        );
        return careRecipientMapper.toCareRecipientResponse(careRecipient);
    }

    public CareRecipientResponse create(CareRecipientRequest request) {
        Client client = clientRepository.findById(request.clientId()).orElseThrow(
                () -> new NoSuchClientException("Client with id " + request.clientId() + " does not exist")
        );
        CareRecipient careRecipient = new CareRecipient(
                request.firstName(),
                request.lastName(),
                request.dateOfBirth(),
                request.heightCm(),
                request.weightKg(),
                request.gender(),
                request.mobilityLevel(),
                request.dementiaLevel(),
                request.hasMs(),
                request.hasAlzheimer(),
                request.hasParkinson(),
                request.diseasesNotes(),
                request.isSmoker(),
                request.hasPets(),
                request.petsNotes(),
                request.needsTransfer(),
                request.transferType(),
                request.liftingAidsNotes(),
                request.hasCatheter(),
                request.hasStoma(),
                request.useDiapers(),
                request.medicalNotes(),
                client
        );
        CareRecipient savedCareRecipent = careRecipientRepository.save(careRecipient);
        return careRecipientMapper.toCareRecipientResponse(savedCareRecipent);
    }

    public CareRecipientResponse update(Long id, CareRecipientRequest request) {
        CareRecipient careRecipient = careRecipientRepository.findById(id).orElseThrow(
                () -> new NoSuchCareRecipientException("CareRecipient with id " + id + " not found")
        );
        careRecipientMapper.updateCareRecipient(request, careRecipient);
        return careRecipientMapper.toCareRecipientResponse(careRecipient);
    }

    public void deactivateCareRecipient(Long id) {
        CareRecipient careRecipient = careRecipientRepository.findById(id).orElseThrow(
                () -> new NoSuchCareRecipientException("CareRecipient with id " + id + " not found")
        );
        careRecipient.deactivate();
    }

}
