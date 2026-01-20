package com.nttdata.application.usecases.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;

public class RegisterClientRoleClient {
    private final ClientRepository repository;

    public RegisterClientRoleClient(ClientRepository repository) {
        this.repository = repository;
    }

    public Client register(Client client) {
        return repository.registerClientRoleClient(client);
    }
}
