package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.client.*;
import com.nttdata.domain.client.Client;
import com.nttdata.domain.client.ClientFactory;
import com.nttdata.infra.presentation.dtos.address.AddressDto;
import com.nttdata.infra.presentation.dtos.client.ClientDto;
import com.nttdata.infra.presentation.dtos.client.ClientUpdateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientControllerTest {

    @Mock
    private ListClients listClients;
    @Mock
    private ClientFactory clientFactory;
    @Mock
    private RegisterClientRoleClient registerClientRoleClient;
    @Mock
    private DeleteClient deleteClient;
    @Mock
    private UpdateClient updateClient;
    @Mock
    private ExistsClient existsClient;
    @Mock
    private VerifyClientActive verifyClientActive;

    @InjectMocks
    private ClientController clientController;

    private Client mockClient;
    private ClientDto mockClientDto;
    private ClientUpdateDto mockClientUpdateDto;

    @BeforeEach
    void setUp() {
        mockClient = new Client();
        mockClient.setId(1L);
        mockClient.setName("Test Client");
        mockClient.setCpf("12345678900");

        AddressDto addressDto = new AddressDto("Street", "123", "Details", "Neighborhood", "City", "State", "12345-678", "Country");
        mockClientDto = new ClientDto("Name", "email@example.com", addressDto, "password", "12345678900", "12345678900", LocalDate.now(), "11987654321");
        mockClientUpdateDto = new ClientUpdateDto("Updated Name", "updated@example.com", addressDto, "updatedUser", "newpassword", "12345678900", LocalDate.now(), "11987654321");
    }

    @Test
    void shouldReturnTrueWhenClientExistsAndIsActive() {
        Long clientId = 1L;
        when(existsClient.byId(clientId)).thenReturn(true);
        when(verifyClientActive.verifyById(clientId)).thenReturn(true);

        ResponseEntity<Boolean> response = clientController.checkClientExists(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
        verify(existsClient).byId(clientId);
        verify(verifyClientActive).verifyById(clientId);
    }

    @Test
    void shouldReturnFalseWhenClientExistsAndIsInactive() {
        Long clientId = 2L;
        when(existsClient.byId(clientId)).thenReturn(true);
        when(verifyClientActive.verifyById(clientId)).thenReturn(false);

        ResponseEntity<Boolean> response = clientController.checkClientExists(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(existsClient).byId(clientId);
        verify(verifyClientActive).verifyById(clientId);
    }

    @Test
    void shouldReturnFalseWhenClientDoesNotExist() {
        Long clientId = 3L;
        when(existsClient.byId(clientId)).thenReturn(false);

        ResponseEntity<Boolean> response = clientController.checkClientExists(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(false, response.getBody());
        verify(existsClient).byId(clientId);
    }

    @Test
    void shouldReturnListOfClients() {
        List<Client> clients = Collections.singletonList(mockClient);
        when(listClients.clientList()).thenReturn(clients);

        ResponseEntity response = clientController.listClients();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
        verify(listClients).clientList();
    }

    @Test
    void shouldRegisterClientSuccessfully() {
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromPath("/clients");
        Client clientToRegister = new Client();
        clientToRegister.setName(mockClientDto.name());
        clientToRegister.setCpf(mockClientDto.cpf());

        when(clientFactory.factory(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(clientToRegister);
        when(registerClientRoleClient.register(any(Client.class))).thenReturn(mockClient);

        ResponseEntity<?> response = clientController.registerClient(mockClientDto, uriBuilder);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getHeaders().getLocation());
        assertEquals("Client registered successfully: " + mockClient, response.getBody());
        verify(clientFactory).factory(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
        verify(registerClientRoleClient).register(clientToRegister);
    }

    @Test
    void shouldDeleteClientSuccessfully() {
        Long clientId = 1L;

        ResponseEntity response = clientController.deleteClient(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Client with ID " + clientId + " deleted successfully.", response.getBody());
        verify(deleteClient).deleteClient(clientId);
    }

    @Test
    void shouldUpdateClientSuccessfully() {
        Long clientId = 1L;
        Client clientDataForUpdate = new Client();
        clientDataForUpdate.setName(mockClientUpdateDto.name());
        clientDataForUpdate.setCpf(mockClientUpdateDto.cpf());

        Client updatedClient = new Client();
        updatedClient.setId(clientId);
        updatedClient.setName(mockClientUpdateDto.name());
        updatedClient.setCpf(mockClientUpdateDto.cpf());

        when(clientFactory.factory(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any()))
                .thenReturn(clientDataForUpdate);
        when(updateClient.update(any(Client.class), eq(clientId))).thenReturn(updatedClient);

        ResponseEntity response = clientController.updateClient(mockClientUpdateDto, clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Client updated successfully: " + updatedClient, response.getBody());
        verify(clientFactory).factory(any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any(), any());
        verify(updateClient).update(clientDataForUpdate, clientId);
    }
}