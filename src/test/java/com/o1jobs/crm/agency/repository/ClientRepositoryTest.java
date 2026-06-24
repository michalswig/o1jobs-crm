package com.o1jobs.crm.agency.repository;

import com.o1jobs.crm.agency.domain.Client;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.Optional;

@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository clientRepository;

    @Test
    void should_create_find_client_By_Id() {
        //given
        Client client = new Client(
                "TestName",
                "testEmail",
                "testNumber",
                "testCountry",
                "testCity",
                "testPostalCode",
                "testAdress",
                "testNotes",
                null
        );
        //when
        Client savedClient = clientRepository.save(client);
        Optional<Client> clientRepositoryById = clientRepository.findById(savedClient.getId());
        //then
        Assertions.assertEquals(clientRepositoryById.get(), client);
    }

}