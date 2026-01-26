package com.nttdata.config;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.domain.bank.BankFactory;
import com.nttdata.infra.service.PayBalance;
import com.nttdata.infra.service.PayCredit;
import com.nttdata.infra.service.Validate;
import com.nttdata.infra.service.method.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class processorConfig {
    @Bean
    public Boleto boleto(PayBalance payBalance, MockApi mockApi) {
        return new Boleto(payBalance, mockApi);
    }

    @Bean
    public Cash cash(PayBalance payBalance, MockApi mockApi) {
        return new Cash(payBalance, mockApi);
    }

    @Bean
    public DebitCard debitCard(PayBalance payBalance, MockApi mockApi) {
        return new DebitCard(payBalance, mockApi);
    }

    @Bean
    public PayBalance payBalance(Faild faild, Completed completed, MockApi mockApi) {
        return new PayBalance(faild, completed, mockApi);
    }
    @Bean
    public MockApi mockApi(RestTemplate restTemplate, Validate validate){
        return new MockApi(restTemplate, validate);
    }

    @Bean
    public CreditCard creditCard(PayCredit payCredit, Validate validate) {
        return new CreditCard(payCredit, validate);
    }

    @Bean
    public PayCredit payCredit(Faild faild, Completed completed, MockApi mockApi) {
        return new PayCredit(faild, completed, mockApi);
    }

    @Bean
    public Pix pix(PayBalance payBalance, MockApi mockApi) {
        return new Pix(payBalance, mockApi);
    }

    @Bean
    public Validate validate(RestTemplate restTemplate, BankFactory bankFactory) {
        return new Validate(restTemplate, bankFactory);
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
