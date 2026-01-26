package com.nttdata.infra.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.presentation.dto.kafka.TransactionKafkaDto;
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

    public void request(Transaction transaction) {
        String topicName = "transaction-requested";
        try {
            TransactionKafkaDto dto = new TransactionKafkaDto(transaction.getId(),
                    transaction.getSourceAccountId(), transaction.getDestinationAccountId(), transaction.getValue(),
                    transaction.getCurrency(),transaction.getAppliedExchangeRate(),transaction.getConvertedValue(),
                    transaction.getDescription(),transaction.getTransactionDate().toString(),transaction.getStatus(),transaction.getType(),
                    transaction.getMethod());

            String transactionJson = objectMapper.writeValueAsString(dto);

            kafkaTemplate.send(topicName, transactionJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize transaction for sending to Kafka", e);
        }
    }

}