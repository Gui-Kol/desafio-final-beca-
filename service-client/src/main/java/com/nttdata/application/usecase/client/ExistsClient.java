package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;

public class ExistsClient {
    private final ClientRepository repository;

    public ExistsClient(ClientRepository repository) {
        this.repository = repository;
    }

    public boolean byId(Long id) {
        return repository.existsClientByid(id);
    }

}
