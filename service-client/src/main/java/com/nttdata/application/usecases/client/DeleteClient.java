package com.nttdata.application.usecases.client;

import com.nttdata.application.repository.ClientRepository;

public class DeleteClient {
    private final ClientRepository repository;

    public DeleteClient(ClientRepository repository) {
        this.repository = repository;
    }

    public void deleteClient(Long id) {
        repository.deleteClient(id);
    }

}
