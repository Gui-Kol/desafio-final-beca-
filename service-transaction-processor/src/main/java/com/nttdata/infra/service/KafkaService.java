package com.nttdata.infra.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.TransactionFactory;
import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.infra.presentation.dto.kafka.TransactionKafkaDto;
import com.nttdata.infra.service.method.*;
import jakarta.transaction.Transactional;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaService {
    private final TransactionFactory transactionFactory;
    private final DebitCard debitCard;
    private final CreditCard creditCard;
    private final Pix pix;
    private final Boleto boleto;
    private final Cash cash;
    private final ObjectMapper objectMapper;

    public KafkaService(TransactionFactory transactionFactory, DebitCard debitCard, CreditCard creditCard, Pix pix, Boleto boleto, Cash cash, ObjectMapper objectMapper) {
        this.transactionFactory = transactionFactory;
        this.debitCard = debitCard;
        this.creditCard = creditCard;
        this.pix = pix;
        this.boleto = boleto;
        this.cash = cash;
        this.objectMapper = objectMapper;
    }


    @KafkaListener(topics = "transaction-requested", groupId = "transaction-processor-group")
    @Transactional
    public void consumer(String json) {
        System.out.println(json);
        try {
            TransactionKafkaDto dto = objectMapper.readValue(json, TransactionKafkaDto.class);
            Transaction transaction =  transactionFactory.factory(dto.id(), dto.sourceAccountId(), dto.destinationAccountId(), dto.convertedValue(), dto.currency(),
                    dto.description(), dto.type(),dto.method());
            try {
                if (transaction.getMethod().equals(PaymentMethod.DEBIT_CARD)){
                    debitCard.pay(transaction);
                }else if (transaction.getMethod().equals(PaymentMethod.CREDIT_CARD)){
                    creditCard.pay(transaction);
                }else if (transaction.getMethod().equals(PaymentMethod.PIX)){
                    pix.pay(transaction);
                }else if (transaction.getMethod().equals(PaymentMethod.BOLETO)){
                    boleto.pay(transaction);
                }else {
                    cash.pay(transaction);
                }
            }catch (Exception e){
                System.out.println(e.getMessage() + e);
            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
}
