package com.nttdata.configs;

import com.nttdata.infra.gateway.address.AddressMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddressConfig {
    @Bean
    public AddressMapper addressMapper(){
        return new AddressMapper();
    }
}
