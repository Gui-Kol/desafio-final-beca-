package com.nttdata.infra.gateway.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;
import com.nttdata.infra.persistence.client.ClientRepositoryEntity;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientRepositoryJpa implements ClientRepository {
    private final ClientRepositoryEntity repository;
    private final ClientMapper mapper;

    public ClientRepositoryJpa(ClientRepositoryEntity repository, ClientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public List<Client> clientList() {
        return repository.findAll()
                .stream()
                .map(mapper::toClient)
                .toList();
    }
}
