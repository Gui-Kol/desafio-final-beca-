package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListClientsTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private ListClients listClients;

    @Test
    void shouldReturnListOfClientsWhenRepositoryHasClients() {
        Client client1 = new Client();
        client1.setCpf("11122233344");
        client1.setName("Client One");

        Client client2 = new Client();
        client2.setCpf("55566677788");
        client2.setName("Client Two");

        List<Client> expectedClients = Arrays.asList(client1, client2);

        when(repository.clientList()).thenReturn(expectedClients);

        List<Client> result = listClients.clientList();

        assertEquals(2, result.size());
        assertEquals(expectedClients, result);
        verify(repository).clientList();
    }

    @Test
    void shouldReturnEmptyListWhenRepositoryHasNoClients() {
        when(repository.clientList()).thenReturn(Collections.emptyList());

        List<Client> result = listClients.clientList();

        assertTrue(result.isEmpty());
        verify(repository).clientList();
    }
}