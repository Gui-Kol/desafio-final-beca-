package com.nttdata.infra.gateway.role;

import com.nttdata.application.repository.RoleRepository;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import com.nttdata.infra.exception.newexception.ClientNotExistsException;
import com.nttdata.infra.persistence.role.RoleEntity;
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
        roleEntity.delete();
        repository.save(roleEntity);
    }

    @Override
    public Role findRoleByClientId(Long id) {
        try {
            return mapper.toRole(repository.findByClientId(id));
        } catch (NullPointerException e) {
            throw new ClientNotExistsException("A client with ID " + id + " does not exist.");
        }
    }

    @Override
    public Role updateClientRole(Long clientId, RoleName roleName) {
        try {
            RoleEntity entity = repository.findByClientId(clientId);
            entity.update(entity.getClientId(), roleName);
            repository.save(entity);
            return mapper.toRole(repository.findByClientId(clientId));
        }catch (NullPointerException e) {
            throw new ClientNotExistsException("A client with ID " + clientId + " does not exist.");
        }
    }
}
