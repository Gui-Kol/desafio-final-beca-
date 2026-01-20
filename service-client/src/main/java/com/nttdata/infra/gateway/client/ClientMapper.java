package com.nttdata.infra.gateway.client;

import com.nttdata.domain.client.Client;
import com.nttdata.infra.gateway.address.AddressMapper;
import com.nttdata.infra.gateway.role.RoleMapper;
import com.nttdata.infra.persistence.client.ClientEntity;

public class ClientMapper {
    private final AddressMapper addressMapper;
    private final RoleMapper roleMapper;

    public ClientMapper(AddressMapper addressMapper, RoleMapper roleMapper) {
        this.addressMapper = addressMapper;
        this.roleMapper = roleMapper;
    }

    public Client toClient(ClientEntity entity) {
        return new Client(entity.getId(), entity.getName(), entity.getEmail(), addressMapper.address(entity.getAddress()),
                entity.getUsername(), entity.getPassword(), entity.getCpf(), entity.getBirthDay(),
                entity.getTelephone(), entity.getCreationDate(), entity.getLastUpdateDate(), entity.isActive(),
                roleMapper.toRole(entity.getRole()), entity.getLastLoginDate()
        );
    }

    public ClientEntity toEntity(Client client) {
        return new ClientEntity(client.getId(), client.getName(), client.getEmail(), addressMapper.entity(client.getAddress()),
                client.getUsername(), client.getPassword(), client.getCpf(), client.getBirthDay(),
                client.getTelephone(), client.getCreationDate(), client.getLastUpdateDate(), client.isActive(),
                roleMapper.toRoleEntity(client.getRoles()), client.getLastLoginDate()
        );
    }

}

