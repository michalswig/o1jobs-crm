package com.o1jobs.crm.agency.service;

import com.o1jobs.crm.agency.domain.Client;
import com.o1jobs.crm.agency.domain.Intermediary;
import com.o1jobs.crm.agency.domain.IntermediaryType;
import com.o1jobs.crm.agency.dto.ClientMapper;
import com.o1jobs.crm.agency.dto.ClientRequest;
import com.o1jobs.crm.agency.dto.ClientResponse;
import com.o1jobs.crm.agency.repository.ClientRepository;
import com.o1jobs.crm.agency.repository.IntermediaryRepository;
import com.o1jobs.crm.exception.NoSuchIntermediaryException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    @Mock
    private ClientRepository clientRepository;
    @Mock
    private IntermediaryRepository intermediaryRepository;
    @Mock
    private ClientMapper clientMapper;

    @InjectMocks
    private ClientService clientService;

    @Test
    void create_client_when_intermediary_is_null() {
        //given
        Client testClient = getClient();
        ClientRequest testClientRequest = getClientRequestIntermediaryNull();
        ClientResponse testClientResponse = getClientResponse();
        when(clientRepository.save(any())).thenReturn(testClient);
        when(clientMapper.toClientResponse(any())).thenReturn(testClientResponse);
        //when
        ClientResponse result = clientService.create(testClientRequest);
        //then
        Assertions.assertEquals(testClientResponse.city(), result.city());
        verify(clientRepository, times(1)).save(any());
    }

    @Test
    void create_client_when_intermediary_is_not_null() {
        //given
        Intermediary testIntermediary = getIntermediary();
        Client testClient = getClient();
        ClientRequest testClientRequest = getClientRequest();
        ClientResponse testClientResponse = getClientResponse();
        when(intermediaryRepository.findById(any())).thenReturn(Optional.of(testIntermediary));
        when(clientRepository.save(any())).thenReturn(testClient);
        when(clientMapper.toClientResponse(any())).thenReturn(testClientResponse);
        //when
        ClientResponse result = clientService.create(testClientRequest);
        //then
        Assertions.assertEquals(testClientResponse.city(), result.city());
        verify(clientRepository, times(1)).save(any());
        verify(intermediaryRepository, times(1)).findById(any());
    }

    @Test
    void create_client_when_intermediary_not_exists_in_db() {
        //given
        ClientRequest testClientRequest = getClientRequest();
        when(intermediaryRepository.findById(any())).thenReturn(Optional.empty());
        //when then
        Assertions.assertThrows(
                NoSuchIntermediaryException.class, () -> clientService.create(testClientRequest));
        verify(clientRepository, times(0)).save(any());
        verify(intermediaryRepository, times(1)).findById(any());
    }

    private static ClientResponse getClientResponse() {
        return new ClientResponse(
                1L,
                "testName",
                "test@wp.pl",
                "123456",
                "Polska",
                "Warszawa",
                "90-000",
                "streetTest 123",
                "good client",
                null
        );
    }

    private static ClientRequest getClientRequest() {
        return new ClientRequest(
                "testName",
                "test@wp.pl",
                "123456",
                "Polska",
                "Warszawa",
                "90-000",
                "streetTest 123",
                "good client",
                1L
        );
    }

    private static ClientRequest getClientRequestIntermediaryNull() {
        return new ClientRequest(
                "testName",
                "test@wp.pl",
                "123456",
                "Polska",
                "Warszawa",
                "90-000",
                "streetTest 123",
                "good client",
                null
        );
    }

    private static Client getClient() {
        return new Client(
                "testName",
                "test@wp.pl",
                "123456",
                "Polska",
                "Warszawa",
                "90-000",
                "streetTest 123",
                "good client",
                null
        );
    }

    private static Intermediary getIntermediary() {
        return new Intermediary(
                1L, "testNotes", "123456", "test@email.com", "testAddress",
                "00-000", "cityName", "testCountry", "testName", IntermediaryType.PARTNER
        );
    }

}

