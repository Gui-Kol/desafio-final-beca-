package com.nttdata.application.usecases.role;

import com.nttdata.application.repository.RoleRepository;
import com.nttdata.domain.role.Role;

public class RegisterRoleClient {
    private final RoleRepository repository;

    public RegisterRoleClient(RoleRepository repository) {
        this.repository = repository;
    }

    public Role register(Role role) {
        return repository.registerRoleClient(role);
    }

}
