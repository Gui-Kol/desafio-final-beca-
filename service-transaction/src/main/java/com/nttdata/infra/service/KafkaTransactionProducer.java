package com.nttdata.infra.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.presentation.dto.TransactionKafkaDto;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaTransactionProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public KafkaTransactionProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendTransactionRequested(Transaction transaction) {
        String topicName = "transaction-requested";
        try {
            TransactionKafkaDto transactionKafkaDto = new TransactionKafkaDto(transaction.getId(),
                    transaction.getSourceAccountId(), transaction.getDestinationAccountId(), transaction.getValue(),
                    transaction.getCurrency(),transaction.getAppliedExchangeRate(),transaction.getConvertedValue(),
                    transaction.getDescription(),transaction.getTransactionDate().toString(),transaction.getStatus(),transaction.getType(),
                    transaction.getMethod());

            String transactionJson = objectMapper.writeValueAsString(transactionKafkaDto);

            kafkaTemplate.send(topicName, transactionJson);

            System.out.println("Transaction sent to Kafka: " + transactionJson);

        } catch (JsonProcessingException e) {
            System.err.println("Error serializing transaction to JSON: " + e.getMessage());
            throw new RuntimeException("Failed to serialize transaction for sending to Kafka", e);
        }
    }
}