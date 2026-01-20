package com.nttdata.infra.gateway.role;

import com.nttdata.application.repository.RoleRepository;
import com.nttdata.domain.role.Role;
import com.nttdata.infra.persistence.role.RoleRepositoryEntity;

public class RoleRepositoryJpa implements RoleRepository {
    private final RoleRepositoryEntity repository;
    private final RoleMapper mapper;

    public RoleRepositoryJpa(RoleRepositoryEntity repository, RoleMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public Role registerRoleClient(Role role) {
        var roleSaved = repository.save(mapper.toRoleEntity(role));
        return mapper.toRole(roleSaved);
    }

    @Override
    public void deleteRoleClient(Long id) {
        var roleEntity = repository.findByClientId(id);
        repository.delete(roleEntity);
    }
}
