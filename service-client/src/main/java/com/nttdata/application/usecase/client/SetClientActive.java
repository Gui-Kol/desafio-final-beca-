package com.nttdata.application.usecase.client;

import com.nttdata.application.repository.ClientRepository;

public class SetClientActive {
    private final ClientRepository repository;
    public SetClientActive(ClientRepository repository) {
        this.repository = repository;
    }

    public void set(Long id, boolean active){
        repository.setClientActive(id, active);
    }
}
