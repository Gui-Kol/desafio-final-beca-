package com.nttdata.infra.gateway.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;
import com.nttdata.infra.persistence.client.ClientRepositoryEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    @Override
    public Client registerClientRoleClient(Client client) {
        System.out.println(client);
        client.setLastLoginDate(LocalDateTime.of(1,1,1,1,1));
        client.setLastUpdateDate(LocalDate.of(1,1,1));
        var entitySaved = repository.save(mapper.toEntity(client));

        return mapper.toClient(entitySaved);
    }

    @Override
    public Client findClientByCpf(String cpf) {
        return mapper.toClient(repository.findByCpf(cpf));
    }
}
