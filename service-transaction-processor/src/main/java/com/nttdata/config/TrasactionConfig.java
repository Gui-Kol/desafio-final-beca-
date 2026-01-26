package com.nttdata.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.infra.service.brasilapi.ApplyExchangeRateService;
import com.nttdata.infra.service.brasilapi.ExchangeRatePurchase;
import com.nttdata.infra.service.kafka.KafkaCancelTransactionProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.client.RestTemplate;

@Configuration
public class TrasactionConfig {
    @Bean
    public ExchangeRatePurchase exchangeRatePurchase(ApplyExchangeRateService applyExchangeRateService){
        return new ExchangeRatePurchase(applyExchangeRateService);
    }
    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }
    @Bean
    public KafkaCancelTransactionProducer kafkaCancelTransactionProducer(KafkaTemplate kafkaTemplate, ObjectMapper objectMapper){
        return new KafkaCancelTransactionProducer(kafkaTemplate,objectMapper);
    }

}
