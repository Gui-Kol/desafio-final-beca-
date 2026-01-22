package com.nttdata.application.usecase.role;

import com.nttdata.application.repository.RoleRepository;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterRoleClientTest {

    @Mock
    private RoleRepository repository;

    @InjectMocks
    private RegisterRoleClient registerRoleClient;

    @Test
    void shouldRegisterRoleSuccessfully() {
        Role roleToRegister = new Role(1L,10L, RoleName.ROLE_CLIENT);
        Role registeredRole = new Role(1L,10L, RoleName.ROLE_CLIENT);


        when(repository.registerRoleClient(roleToRegister)).thenReturn(registeredRole);

        Role result = registerRoleClient.register(roleToRegister);

        assertEquals(registeredRole, result);
        verify(repository).registerRoleClient(roleToRegister);
    }
}