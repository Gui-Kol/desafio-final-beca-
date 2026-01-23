package com.nttdata.config;

import com.nttdata.infra.service.brasilapi.ApplyExchangeRateService;
import com.nttdata.infra.service.pdf.PdfGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ServiceConfig {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    @Bean
    public ApplyExchangeRateService applyExchangeRateService(RestTemplate restTemplate){
        return new ApplyExchangeRateService(restTemplate);
    }
    @Bean
    public PdfGenerator pdfGenerator(){
        return new PdfGenerator();
    }

}
