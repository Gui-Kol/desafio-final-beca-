package com.nttdata.application.usecase.role;

import com.nttdata.application.repository.RoleRepository;

public class DeleteRoleClient {
    private final RoleRepository repository;
    public DeleteRoleClient(RoleRepository repository) {
        this.repository = repository;
    }

    public void delete(Long id) {
        repository.deleteRoleClient(id);
    }
}
