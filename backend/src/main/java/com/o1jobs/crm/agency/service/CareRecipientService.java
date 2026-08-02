package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.CareRecipient;
import com.o1jobs.crm.agency.domain.Client;
import com.o1jobs.crm.agency.dto.CareRecipientMapper;
import com.o1jobs.crm.agency.dto.CareRecipientRequest;
import com.o1jobs.crm.agency.dto.CareRecipientResponse;
import com.o1jobs.crm.agency.repository.CareRecipientRepository;
import com.o1jobs.crm.agency.repository.ClientRepository;
import com.o1jobs.crm.agency.specification.CareRecipientSpecifications;
import com.o1jobs.crm.exception.NoSuchCareRecipientException;
import com.o1jobs.crm.exception.NoSuchClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CareRecipientService {
    private final CareRecipientRepository careRecipientRepository;
    private final ClientRepository clientRepository;
    private final CareRecipientMapper careRecipientMapper;

    @Transactional(readOnly = true)
    public Page<CareRecipientResponse> getAll(Pageable pageable) {
        return careRecipientRepository.findAll(CareRecipientSpecifications.notDeleted(), pageable)
                .map(careRecipientMapper::toCareRecipientResponse);
    }

    @Transactional(readOnly = true)
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
                request.diseasesNotes(),
                request.smoker(),
                request.hasPets(),
                request.petsNotes(),
                request.liftingAidsNotes(),
                request.medicalNotes(),
                request.requiredCapabilities(),
                client
        );
        CareRecipient savedCareRecipient = careRecipientRepository.save(careRecipient);
        return careRecipientMapper.toCareRecipientResponse(savedCareRecipient);
    }

    public CareRecipientResponse update(Long id, CareRecipientRequest request) {
        CareRecipient careRecipient = careRecipientRepository.findById(id).orElseThrow(
                () -> new NoSuchCareRecipientException("CareRecipient with id " + id + " not found")
        );
        careRecipient.updateDetails(
                request.firstName(), request.lastName(), request.dateOfBirth(), request.heightCm(),
                request.weightKg(), request.gender(), request.mobilityLevel(), request.dementiaLevel(),
                request.diseasesNotes(), request.smoker(), request.hasPets(), request.petsNotes(),
                request.liftingAidsNotes(), request.medicalNotes()
        );
        careRecipient.updateRequiredCapabilities(request.requiredCapabilities());

        if (!request.clientId().equals(careRecipient.getClient().getId())) {
            Client client = clientRepository.findById(request.clientId()).orElseThrow(
                    () -> new NoSuchClientException("Client with id " + request.clientId() + " does not exist")
            );
            careRecipient.assignClient(client);
        }

        return careRecipientMapper.toCareRecipientResponse(careRecipient);
    }

    public void deactivateCareRecipient(Long id) {
        CareRecipient careRecipient = careRecipientRepository.findById(id).orElseThrow(
                () -> new NoSuchCareRecipientException("CareRecipient with id " + id + " not found")
        );
        careRecipient.deactivate();
    }

    public CareRecipient getEntityById(Long id) {
        return careRecipientRepository.findById(id).orElseThrow(
                () -> new NoSuchCareRecipientException("CareRecipient with id " + id + " not found")
        );
    }
}