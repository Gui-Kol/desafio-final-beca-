package com.nttdata.infra.gateway.client;

import com.nttdata.domain.client.Client;
import com.nttdata.infra.gateway.address.AddressMapper;
import com.nttdata.infra.persistence.client.ClientEntity;

public class ClientMapper {
    private final AddressMapper addressMapper;

    public ClientMapper(AddressMapper addressMapper) {
        this.addressMapper = addressMapper;
    }

    public Client toClient(ClientEntity entity) {
        return new Client(entity.getId(), entity.getName(), entity.getEmail(), addressMapper.address(entity.getAddress()),
                entity.getUsername(), entity.getPasssword(), entity.getCpf(), entity.getBirthDay(),
                entity.getTelephone(), entity.getCreationDate(), entity.getLastUpdateDate(), entity.isActive(),
                entity.getRole(), entity.getLastLoginDate()
        );
    }

    public ClientEntity toClientEntity(Client client) {
        return new ClientEntity(client.getId(), client.getName(), client.getEmail(), addressMapper.entity(client.getAddress()),
                client.getUsername(), client.getPasssword(), client.getCpf(), client.getBirthDay(),
                client.getTelephone(), client.getCreationDate(), client.getLastUpdateDate(), client.isActive(),
                client.getRole(), client.getLastLoginDate()
        );
    }

}

