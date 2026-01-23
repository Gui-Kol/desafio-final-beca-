package com.nttdata.config;

import com.nttdata.application.repository.TransactionRepository;
import com.nttdata.application.usecase.CancelTransaction;
import com.nttdata.application.usecase.ListTransactions;
import com.nttdata.application.usecase.ListTransactionsPdf;
import com.nttdata.application.usecase.TransactionCase;
import com.nttdata.domain.transaction.TransactionFactory;
import com.nttdata.infra.gateway.TransactionMapper;
import com.nttdata.infra.gateway.TransactionRepositoryJpa;
import com.nttdata.infra.persistence.client.TransactionRepositoryEntity;
import com.nttdata.infra.service.ClientValidationService;
import com.nttdata.infra.service.pdf.PdfGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {
    @Bean
    public TransactionCase pay(TransactionRepositoryJpa repository) {
        return new TransactionCase(repository);
    }

    @Bean
    public TransactionRepositoryJpa repositoryJpa(ClientValidationService clientValidationService, TransactionRepositoryEntity repository,
                                                  PdfGenerator pdfGenerator, TransactionMapper mapper) {
        return new TransactionRepositoryJpa(clientValidationService, repository, pdfGenerator ,mapper);
    }

    @Bean
    public TransactionFactory transactionFactory() {
        return new TransactionFactory();
    }

    @Bean
    public TransactionMapper transactionMapper() {
        return new TransactionMapper();
    }

    @Bean
    public CancelTransaction cancelTransaction(TransactionRepository repository) {
        return new CancelTransaction(repository);
    }
    @Bean
    public ListTransactions listTransactions(TransactionRepository repository){
        return new ListTransactions(repository);
    }
    @Bean
    public ListTransactionsPdf listTransactionsPdf(TransactionRepository repository){
        return new ListTransactionsPdf(repository);
    }

}
