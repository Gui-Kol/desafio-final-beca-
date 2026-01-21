package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;

public class UpdateClient {
    private final ClientRepository repository;

    public UpdateClient(ClientRepository repository) {
        this.repository = repository;
    }

    public Client update(Client client, Long id) {
        return repository.updateClient(client, id);
    }

}
