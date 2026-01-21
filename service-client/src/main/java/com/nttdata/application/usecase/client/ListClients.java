package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;

import java.util.List;

public class ListClients {
    private final ClientRepository repository;

    public ListClients(ClientRepository repository) {
        this.repository = repository;
    }

    public List<Client> clientList() {
        return repository.clientList();
    }



}
