package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.CancelTransaction;
import com.nttdata.application.usecase.ListTransactions;
import com.nttdata.application.usecase.ListTransactionsPdf;
import com.nttdata.application.usecase.TransactionCase;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.TransactionFactory;
import com.nttdata.infra.exception.ClientNotExistsException;
import com.nttdata.infra.exception.TransactionException;
import com.nttdata.infra.presentation.dto.transaction.TransactionDto;
import com.nttdata.infra.service.kafka.KafkaCancelTransactionProducer;
import com.nttdata.infra.service.kafka.KafkaTransactionProducer;
import com.nttdata.infra.service.brasilapi.ExchangeRatePurchase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionCase transactionCase;
    private final CancelTransaction cancelTransaction;
    private final TransactionFactory transactionFactory;
    private final ExchangeRatePurchase exchangeRatePurchase;
    private final KafkaTransactionProducer kafkaTransactionProducer;
    private final KafkaCancelTransactionProducer kafkaCancelTransactionProducer;
    private final ListTransactions listTransactions;
    private final ListTransactionsPdf pdf;

    public TransactionController(TransactionCase transactionCase, CancelTransaction cancelTransaction, TransactionFactory transactionFactory, ExchangeRatePurchase exchangeRatePurchase, KafkaTransactionProducer kafkaTransactionProducer, KafkaCancelTransactionProducer kafkaCancelTransactionProducer, ListTransactions listTransactions, ListTransactionsPdf pdf) {
        this.transactionCase = transactionCase;
        this.cancelTransaction = cancelTransaction;
        this.transactionFactory = transactionFactory;
        this.exchangeRatePurchase = exchangeRatePurchase;
        this.kafkaTransactionProducer = kafkaTransactionProducer;
        this.kafkaCancelTransactionProducer = kafkaCancelTransactionProducer;
        this.listTransactions = listTransactions;
        this.pdf = pdf;
    }


    @PostMapping()
    public ResponseEntity payment(@RequestBody TransactionDto dto) {
        Transaction transaction = transactionFactory.factory(null,dto.sourceAccountId(),dto.destinationAccountId(),dto.value(),dto.currency(),dto.description(),
                dto.type(), dto.method());
        try {
            var response = transactionCase
                    .transaction(exchangeRatePurchase.purchase(transaction));
            kafkaTransactionProducer.request(response);
            return ResponseEntity.ok(response);
        } catch (TransactionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{transactionId}")
    public ResponseEntity cancel(@PathVariable Long transactionId){
        try {
            var transactionCancel = cancelTransaction.cancel(transactionId);
            kafkaCancelTransactionProducer.cancel(transactionCancel);
            return ResponseEntity.ok(transactionCancel);
        }catch (TransactionException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{clientId}")
    public ResponseEntity listTransaction(@PathVariable Long clientId){
        try {
            List<Transaction> transactions = listTransactions.byClientId(clientId);
            return ResponseEntity.ok(transactions);
        }catch (ClientNotExistsException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @GetMapping("/{clientId}/pdf")
    public ResponseEntity listTransactionPdf(@PathVariable Long clientId){
        try {
            pdf.create(clientId);
            return ResponseEntity.ok().build();
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }




}
