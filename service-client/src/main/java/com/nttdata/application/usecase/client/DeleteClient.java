package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.domain.exception.ClientInactiveException;

public class DeleteClient {
    private final ClientRepository repository;

    public DeleteClient(ClientRepository repository) {
        this.repository = repository;
    }

    public void deleteClient(Long id) {
        boolean active = repository.verifyActiveById(id);
        if (active) {
            repository.deleteClient(id);
        }else {
            throw new ClientInactiveException("Client already inactive!");
        }
    }

}
