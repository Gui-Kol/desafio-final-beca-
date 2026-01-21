package com.nttdata.application.usecases.role;

import com.nttdata.application.repository.RoleRepository;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;

public class UpdateClientRole {
    private final RoleRepository repository;
    public UpdateClientRole(RoleRepository repository) {
        this.repository = repository;
    }

    public Role update(Long clientId, RoleName roleName){
        return repository.updateClientRole(clientId, roleName);
    }

}
