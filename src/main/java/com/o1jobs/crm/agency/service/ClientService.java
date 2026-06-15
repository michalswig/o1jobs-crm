package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.Client;
import com.o1jobs.crm.agency.domain.Intermediary;
import com.o1jobs.crm.agency.dto.ClientMapper;
import com.o1jobs.crm.agency.dto.ClientRequest;
import com.o1jobs.crm.agency.dto.ClientResponse;
import com.o1jobs.crm.agency.repository.ClientRepository;
import com.o1jobs.crm.agency.repository.IntermediaryRepository;
import com.o1jobs.crm.exception.IntermediaryNotFoundException;
import com.o1jobs.crm.exception.NoSuchClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {
    private final ClientRepository clientRepository;
    private final IntermediaryRepository intermediaryRepository;
    private final ClientMapper clientMapper;

    public ClientResponse create(ClientRequest request) {
        Intermediary intermediary = null;
        if (request.intermediary_id() != null) {
            intermediary = intermediaryRepository.findById(request.intermediary_id()).orElseThrow(
                    () -> new IntermediaryNotFoundException("Intermediary with id " + request.intermediary_id())
            );
        }
        Client client = new Client(
                request.name(),
                request.email(),
                request.phoneNumber(),
                request.country(),
                request.city(),
                request.postalCode(),
                request.streetAddress(),
                request.notes(),
                intermediary
        );
        return clientMapper.toClientResponse(clientRepository.save(client));
    }

    @Transactional(readOnly = true)
    public ClientResponse getById(Long id) {
        return clientMapper.toClientResponse(getClientById(id));
    }

    public ClientResponse update(ClientRequest request, Long id) {
        Client clientToUpdate = getClientById(id);
        clientMapper.updateClient(request, clientToUpdate);
        if (request.intermediary_id() != null) {
            Intermediary intermediary = intermediaryRepository.findById(request.intermediary_id()).orElseThrow(
                    () -> new IntermediaryNotFoundException("Intermediary with id " + request.intermediary_id())
            );
            clientToUpdate.assignIntermediary(intermediary);
        }
        return clientMapper.toClientResponse(clientToUpdate);
    }

    public void deactivate(Long id) {
        getClientById(id).deactivateClient();
    }

    private Client getClientById(Long id) {
        return clientRepository.findById(id).orElseThrow(
                () -> new NoSuchClientException("Client with id " + id + " does not exist"));
    }

}
