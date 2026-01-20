package com.nttdata.configs;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.application.usecases.FindClientByCpf;
import com.nttdata.application.usecases.ListClients;
import com.nttdata.application.usecases.RegisterClientRoleClient;
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
    public ListClients listClients(ClientRepository repository){
        return new ListClients(repository);
    }
    @Bean
    public ClientRepositoryJpa clientRepositoryJpa(ClientRepositoryEntity repository, ClientMapper mapper){
        return new ClientRepositoryJpa(repository, mapper);
    }
    @Bean
    public ClientMapper clientMapper(AddressMapper addressMapper, RoleMapper roleMapper){
        return new ClientMapper(addressMapper, roleMapper);
    }
    @Bean
    public AddressMapper addressMapper(){
        return new AddressMapper();
    }
    @Bean
    public RoleMapper roleMapper(){
        return new RoleMapper();
    }
    @Bean
    public RegisterClientRoleClient registerClient(ClientRepository repository){
        return new RegisterClientRoleClient(repository);
    }
    @Bean
    public FindClientByCpf findClientByCpf(ClientRepository repository){
        return new FindClientByCpf(repository);
    }

}
