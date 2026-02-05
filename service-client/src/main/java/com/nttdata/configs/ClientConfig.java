package com.nttdata.configs;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.application.usecase.client.*;
import com.nttdata.application.usecase.role.DeleteRoleClient;
import com.nttdata.application.usecase.role.RegisterRoleClient;
import com.nttdata.domain.client.ClientFactory;
import com.nttdata.infra.gateway.address.AddressMapper;
import com.nttdata.infra.gateway.client.ClientMapper;
import com.nttdata.infra.gateway.client.ClientRepositoryJpa;
import com.nttdata.infra.gateway.role.RoleMapper;
import com.nttdata.infra.persistence.client.ClientRepositoryEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ClientConfig {
    @Bean
    public ListClients listClients(ClientRepository repository) {
        return new ListClients(repository);
    }

    @Bean
    public ClientRepositoryJpa clientRepositoryJpa(ClientRepositoryEntity repository, ClientMapper mapper, RegisterRoleClient registerRoleClient,
                                                   DeleteRoleClient deleteRoleClient, PasswordEncoder passwordEncoder) {
        return new ClientRepositoryJpa(repository, mapper, registerRoleClient,
                deleteRoleClient, passwordEncoder);
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
    @Bean
    public ExistsClient existsClient(ClientRepository repository){
        return new ExistsClient(repository);
    }
    @Bean
    public VerifyClientActive verifyClientActive(ClientRepository repository){
        return new VerifyClientActive(repository);
    }
    @Bean
    public ValidLogin validLogin(ClientRepository repository){
        return new ValidLogin(repository);
    }
}
