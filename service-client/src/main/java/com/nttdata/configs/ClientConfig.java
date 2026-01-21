package com.nttdata.configs;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.application.usecases.client.*;
import com.nttdata.application.usecases.role.DeleteRoleClient;
import com.nttdata.application.usecases.role.RegisterRoleClient;
import com.nttdata.domain.client.ClientFactory;
import com.nttdata.infra.gateway.address.AddressMapper;
import com.nttdata.infra.gateway.client.ClientMapper;
import com.nttdata.infra.gateway.client.ClientRepositoryJpa;
import com.nttdata.infra.gateway.role.RoleMapper;
import com.nttdata.infra.persistence.client.ClientRepositoryEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ClientConfig {
    @Bean
    public ListClients listClients(ClientRepository repository) {
        return new ListClients(repository);
    }

    @Bean
    public ClientRepositoryJpa clientRepositoryJpa(ClientRepositoryEntity repository, ClientMapper mapper, RegisterRoleClient registerRoleClient,
                                                   DeleteRoleClient deleteRoleClient) {
        return new ClientRepositoryJpa(repository, mapper, registerRoleClient,
                deleteRoleClient);
    }
    @Bean
    public ClientMapper clientMapper(AddressMapper addressMapper, RoleMapper roleMapper) {
        return new ClientMapper(addressMapper, roleMapper);
    }

    @Bean
    public RegisterClientRoleClient registerClient(ClientRepository repository) {
        return new RegisterClientRoleClient(repository);
    }

    @Bean
    public FindClientByCpf findClientByCpf(ClientRepository repository) {
        return new FindClientByCpf(repository);
    }

    @Bean
    public DeleteClient deleteClient(ClientRepository repository) {
        return new DeleteClient(repository);
    }
    @Bean
    public UpdateClient updateClient(ClientRepository repository){
        return new UpdateClient(repository);
    }
    @Bean
    public ClientFactory clientFabric(){
        return new ClientFactory();
    }
}
