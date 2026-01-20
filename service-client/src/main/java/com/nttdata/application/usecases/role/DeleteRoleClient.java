package com.nttdata.application.usecases.role;

import com.nttdata.application.repository.RoleRepository;
import com.nttdata.domain.role.Role;

public class DeleteRoleClient {
    private final RoleRepository repository;
    public DeleteRoleClient(RoleRepository repository) {
        this.repository = repository;
    }

    public void delete(Long id) {
        repository.deleteRoleClient(id);
    }
}
