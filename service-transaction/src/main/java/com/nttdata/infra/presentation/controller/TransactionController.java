package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.CancelTransaction;
import com.nttdata.application.usecase.TransactionCase;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.TransactionFactory;
import com.nttdata.infra.presentation.dto.TransactionDto;
import com.nttdata.infra.service.brasilapi.ApplyExchangeRateService;
import com.nttdata.infra.service.KafkaTransactionProducer;
import com.nttdata.infra.service.brasilapi.ExchangeRatePurchase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionCase transactionCase;
    private final CancelTransaction cancelTransaction;
    private final TransactionFactory transactionFactory;
    private final ExchangeRatePurchase exchangeRatePurchase;
    private final ApplyExchangeRateService applyExchangeRate;

    public TransactionController(TransactionCase transactionCase, CancelTransaction cancelTransaction, TransactionFactory transactionFactory, ExchangeRatePurchase exchangeRatePurchase, ApplyExchangeRateService applyExchangeRate) {
        this.transactionCase = transactionCase;
        this.cancelTransaction = cancelTransaction;
        this.transactionFactory = transactionFactory;
        this.exchangeRatePurchase = exchangeRatePurchase;
        this.applyExchangeRate = applyExchangeRate;
    }


    @PostMapping()
    public ResponseEntity payment(@RequestBody TransactionDto dto) {
        Transaction transaction = transactionFactory.factory(dto.sourceAccountId(),dto.destinationAccountId(),dto.value(),dto.currency(),dto.description(),
                dto.type(), dto.method());
        applyExchangeRate.apply(transaction);
        try {
            var response = transactionCase.transaction(transaction);
            exchangeRatePurchase.purchase(response);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity cancel(@PathVariable Long transactionId){
        try {
            var canceled = cancelTransaction.cancel(transactionId);
            return ResponseEntity.ok(canceled);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body("There was an error while canceling: " + e.getMessage());
        }

    }


}
