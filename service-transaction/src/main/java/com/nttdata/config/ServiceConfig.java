package com.nttdata.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.infra.service.brasilapi.ApplyExchangeRateService;
import com.nttdata.infra.service.pdf.PdfGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {
    @Bean
    public ApplyExchangeRateService applyExchangeRateService(RestTemplate restTemplate){
        return new ApplyExchangeRateService(restTemplate);
    }
    @Bean
    public PdfGenerator pdfGenerator(){
        return new PdfGenerator();
    }
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }
}
