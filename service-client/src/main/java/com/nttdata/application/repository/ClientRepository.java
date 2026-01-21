package com.nttdata.application.repository;

import com.nttdata.domain.client.Client;

import java.util.List;

public interface ClientRepository {

    List<Client> clientList();

    Client registerClientRoleClient(Client client);

    Client findClientByCpf(String cpf);

    void deleteClient(Long id);

    Client updateClient(Client client, Long id);

    boolean existsClientByid(Long id);
}
