package com.nttdata.infra.gateway.role;


import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.role.Role;
import com.nttdata.domain.role.RoleName;
import com.nttdata.infra.persistence.client.ClientRepositoryEntity;
import com.nttdata.infra.persistence.role.RoleEntity;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {
    public Set<Role> roleStringToRoleSet (Set<String> roleNames,long clientId){
        return roleNames.stream()
                .map(name -> new Role(
                        clientId,
                        RoleName.valueOf(name)
                )).collect(Collectors.toSet());
    }

    public Set<Role> toRole (Set<RoleEntity> entities){
        return entities.stream()
                .map(entity -> new Role(
                        entity.getId(),
                        entity.getClientId(),
                        entity.getName()
                )).collect(Collectors.toSet());
    }
    public Set<RoleEntity> toRoleEntity (Set<Role> roles){
        return roles.stream()
                .map(role -> new RoleEntity(
                        role.getId(),
                        role.getClientId(),
                        role.getName()
                )).collect(Collectors.toSet());
    }

}
