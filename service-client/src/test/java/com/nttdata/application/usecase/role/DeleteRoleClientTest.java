package com.nttdata.application.usecase.role;

import com.nttdata.application.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class DeleteRoleClientTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private DeleteRoleClient deleteRoleClient;

    @Test
    void shouldCallDeleteRoleClientWithCorrectId() {
        Long roleId = 1L;

        deleteRoleClient.delete(roleId);

        verify(repository).deleteRoleClient(roleId);
    }
}