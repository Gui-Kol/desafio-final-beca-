package com.nttdata.application.usecases.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.client.Client;

public class FindClientByCpf {
private final ClientRepository repository;

    public FindClientByCpf(ClientRepository repository) {
        this.repository = repository;
    }

    public Client findClientByCpf(String cpf) {
        return repository.findClientByCpf(cpf);
    }
}
