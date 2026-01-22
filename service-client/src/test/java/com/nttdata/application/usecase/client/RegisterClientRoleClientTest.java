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
class RegisterClientRoleClientTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private RegisterClientRoleClient registerClientRoleClient;

    @Test
    void shouldRegisterClientSuccessfully() {
        Client clientToRegister = new Client();
        clientToRegister.setCpf("12345678900");
        clientToRegister.setName("Test Client");

        Client registeredClient = new Client();
        registeredClient.setCpf("12345678900");
        registeredClient.setName("Test Client");
        registeredClient.setId(1L);

        when(repository.registerClientRoleClient(clientToRegister)).thenReturn(registeredClient);

        Client result = registerClientRoleClient.register(clientToRegister);

        assertEquals(registeredClient, result);
        verify(repository).registerClientRoleClient(clientToRegister);
    }
}