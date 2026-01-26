package com.nttdata.config;

import com.nttdata.infra.service.brasilapi.ApplyExchangeRateService;
import com.nttdata.infra.service.brasilapi.ExchangeRatePurchase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TrasactionConfig {
    @Bean
    public ExchangeRatePurchase exchangeRatePurchase(ApplyExchangeRateService applyExchangeRateService) {
        return new ExchangeRatePurchase(applyExchangeRateService);
    }

}
