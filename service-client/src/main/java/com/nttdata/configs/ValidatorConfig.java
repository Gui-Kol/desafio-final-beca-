package com.nttdata.configs;

import com.nttdata.infra.service.ClientValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidatorConfig {

    @Bean
    public ClientValidator clientValidator(){
        return new ClientValidator();
    }

}
