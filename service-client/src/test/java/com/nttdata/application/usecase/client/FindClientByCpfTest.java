package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FindClientByCpfTest {

    @Mock
    private ClientRepository repository;

    @InjectMocks
    private FindClientByCpf findClientByCpf;

    @Test
    void shouldFindClientByCpfWhenCpfExists() {
        String cpf = "12345678900";
        Client expectedClient = new Client();
        expectedClient.setCpf(cpf);
        expectedClient.setName("Test Client");

        when(repository.findClientByCpf(cpf)).thenReturn(expectedClient);

        Client result = findClientByCpf.findClientByCpf(cpf);

        assertEquals(expectedClient, result);
        verify(repository).findClientByCpf(cpf);
    }

    @Test
    void shouldReturnNullWhenCpfDoesNotExist() {
        String cpf = "00987654321";

        when(repository.findClientByCpf(cpf)).thenReturn(null);

        Client result = findClientByCpf.findClientByCpf(cpf);

        assertNull(result);
        verify(repository).findClientByCpf(cpf);
    }
}