package com.nttdata.config;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.application.usecases.processor.Completed;
import com.nttdata.application.usecases.processor.Faild;
import com.nttdata.application.usecases.processor.payment.*;
import com.nttdata.domain.bank.BankFactory;
import com.nttdata.infra.gateway.processor.PayBalance;
import com.nttdata.infra.gateway.processor.PayCredit;
import com.nttdata.infra.service.MockApiService;
import com.nttdata.infra.service.ValidateService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class processorConfig {
    @Bean
    public Boleto boleto(PayBalance payBalance) {
        return new Boleto(payBalance);
    }

    @Bean
    public Cash cash(PayBalance payBalance) {
        return new Cash(payBalance);
    }

    @Bean
    public DebitCard debitCard(PayBalance payBalance) {
        return new DebitCard(payBalance);
    }

    @Bean
    public PayBalance payBalance(Faild faild, Completed completed, MockApiService mockApiService) {
        return new PayBalance(faild, completed, mockApiService);
    }
    @Bean
    public MockApiService mockApi(RestTemplate restTemplate, ValidateService validateService){
        return new MockApiService(restTemplate, validateService);
    }

    @Bean
    public CreditCard creditCard(PayCredit payCredit) {
        return new CreditCard(payCredit);
    }

    @Bean
    public PayCredit payCredit(Faild faild, Completed completed, MockApiService mockApiService) {
        return new PayCredit(faild, completed, mockApiService);
    }

    @Bean
    public Pix pix(PayBalance payBalance) {
        return new Pix(payBalance);
    }

    @Bean
    public ValidateService validate(RestTemplate restTemplate, BankFactory bankFactory) {
        return new ValidateService(restTemplate, bankFactory);
    }

    @Bean
    public BankFactory bankFactory() {
        return new BankFactory();
    }

    @Bean
    public Faild faild(TransactionRepository repository){
        return new Faild(repository);
    }
    @Bean
    public Completed completed(TransactionRepository repository){
        return new Completed(repository);
    }


}
