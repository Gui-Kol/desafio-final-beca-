package com.nttdata.infra.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.infra.presentation.dto.kafka.CancelTransactionKafkaDto;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaCancelTransactionProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    public KafkaCancelTransactionProducer(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }


    public void cancel(Transaction canceled) {
        String topicName = "transaction-canceled";
        try {
            CancelTransactionKafkaDto dto = new CancelTransactionKafkaDto(canceled.getId(),
                    canceled.getSourceAccountId(), canceled.getDestinationAccountId(),canceled.getStatus(),canceled.getType(),
                    canceled.getMethod());

            String transactionJson = objectMapper.writeValueAsString(dto);

            kafkaTemplate.send(topicName, transactionJson);

        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize cancellation request for sending to Kafka", e);
        }
    }
}
