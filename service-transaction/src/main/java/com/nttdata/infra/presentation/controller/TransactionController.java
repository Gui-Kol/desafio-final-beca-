package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.TransactionCase;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.TransactionFactory;
import com.nttdata.infra.presentation.dto.TransactionDto;
import com.nttdata.infra.service.ApplyExchangeRateService;
import com.nttdata.infra.service.KafkaTransactionProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionCase transactionCase;
    private final TransactionFactory transactionFactory;
    private final KafkaTransactionProducer kafkaTransactionProducer;
    private final ApplyExchangeRateService applyExchangeRate;

    public TransactionController(TransactionCase transactionCase, TransactionFactory transactionFactory, KafkaTransactionProducer kafkaTransactionProducer, ApplyExchangeRateService applyExchangeRate) {
        this.transactionCase = transactionCase;
        this.transactionFactory = transactionFactory;
        this.kafkaTransactionProducer = kafkaTransactionProducer;
        this.applyExchangeRate = applyExchangeRate;
    }

    @PostMapping()
    public ResponseEntity pay(@RequestBody TransactionDto dto) {
        Transaction transaction = transactionFactory.factory(dto.sourceAccountId(),dto.destinationAccountId(),dto.value(),dto.currency(),dto.description(),
                dto.type(), dto.method());
        applyExchangeRate.apply(transaction);
        try {
            var response = transactionCase.transaction(transaction);
            kafkaTransactionProducer.sendTransactionRequested(response);
            return ResponseEntity.ok("Transaction sucessfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }
}
