package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateClientTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private UpdateClient updateClient;

    @Test
    void shouldUpdateClientSuccessfully() {
        Long clientId = 1L;
        Client clientToUpdate = new Client();
        clientToUpdate.setName("Updated Name");
        clientToUpdate.setCpf("11122233344");

        Client updatedClient = new Client();
        updatedClient.setId(clientId);
        updatedClient.setName("Updated Name");
        updatedClient.setCpf("11122233344");

        when(repository.updateClient(clientToUpdate, clientId)).thenReturn(updatedClient);

        Client result = updateClient.update(clientToUpdate, clientId);

        assertEquals(updatedClient, result);
        verify(repository).updateClient(clientToUpdate, clientId);
    }
}