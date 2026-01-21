package com.nttdata.application.usecases.role;

import com.nttdata.application.repository.RoleRepository;
import com.nttdata.domain.role.Role;

public class FindRoleByClientId {
    private final RoleRepository repository;
    public FindRoleByClientId(RoleRepository repository) {
        this.repository = repository;
    }

    public Role find(Long id) {
        return repository.findRoleByClientId(id);
    }
}
