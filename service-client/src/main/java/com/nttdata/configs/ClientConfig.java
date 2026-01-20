package com.nttdata.configs;

import com.nttdata.application.repository.ClientRepository;
import com.nttdata.application.usecases.ListClients;
import com.nttdata.infra.gateway.address.AddressMapper;
import com.nttdata.infra.gateway.client.ClientMapper;
import com.nttdata.infra.gateway.client.ClientRepositoryJpa;
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
    public ClientMapper clientMapper(AddressMapper mapper){
        return new ClientMapper(mapper);
    }
    @Bean
    public AddressMapper addressMapper(){
        return new AddressMapper();
    }

}
