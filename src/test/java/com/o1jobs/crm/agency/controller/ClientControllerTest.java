package com.o1jobs.crm.agency.controller;

import com.o1jobs.crm.agency.dto.ClientRequest;
import com.o1jobs.crm.agency.dto.ClientResponse;
import com.o1jobs.crm.agency.service.ClientService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import tools.jackson.databind.ObjectMapper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ClientController.class)
class ClientControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockitoBean
    ClientService clientService;

//    Następny krok — napisz drugi test kontrolera:
//    POST /clients z niepoprawnym requestem (np. name jest null) → oczekujesz 400 Bad Request.
//    To jest test walidacji. Zanim napiszesz — powiedz mi: czy musisz
//    w ogóle konfigurować when() dla clientService w tym teście? Dlaczego tak lub nie?

    @Test
    void should_return_400_when_creating_client_with_null_name() throws Exception {
        mockMvc.perform(
                        post("/api/v1/clients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getClientRequestWithNullName()))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_create_client_and_return_ok() throws Exception {
        //given when
        when(clientService.create(any())).thenReturn(getClientResponse());

        //then
        mockMvc.perform(
                        post("/api/v1/clients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(getClientRequest()))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.city").value("Warszawa"));

    }

    private static ClientRequest getClientRequestWithNullName() {
        return new ClientRequest(
                null,
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

}