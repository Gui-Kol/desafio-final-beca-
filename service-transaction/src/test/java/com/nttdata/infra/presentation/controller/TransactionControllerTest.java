package com.nttdata.infra.presentation.controller;

import com.nttdata.application.usecase.CancelTransaction;
import com.nttdata.application.usecase.ListTransactions;
import com.nttdata.application.usecase.ListTransactionsPdf;
import com.nttdata.application.usecase.TransactionCase;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.TransactionFactory;
import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.TransactionType;
import com.nttdata.infra.exception.newexception.ClientNotExistsException;
import com.nttdata.infra.exception.newexception.PdfGeneratorException;
import com.nttdata.infra.exception.newexception.TransactionException;
import com.nttdata.infra.presentation.dto.transaction.TransactionDto;
import com.nttdata.infra.service.brasilapi.ExchangeRatePurchase;
import com.nttdata.infra.service.kafka.KafkaCancelTransactionProducer;
import com.nttdata.infra.service.kafka.KafkaTransactionProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionCase transactionCase;
    @Mock
    private CancelTransaction cancelTransaction;
    @Mock
    private TransactionFactory transactionFactory;
    @Mock
    private ExchangeRatePurchase exchangeRatePurchase;
    @Mock
    private KafkaTransactionProducer kafkaTransactionProducer;
    @Mock
    private KafkaCancelTransactionProducer kafkaCancelTransactionProducer;
    @Mock
    private ListTransactions listTransactions;
    @Mock
    private ListTransactionsPdf pdf;

    @InjectMocks
    private TransactionController transactionController;


    @Test
    void paymentShouldReturnOkWhenTransactionIsSuccessful() {
        TransactionDto dto = new TransactionDto(100L, 101L, BigDecimal.valueOf(50.0), "BRL", "Test", TransactionType.TRANSFER, PaymentMethod.PIX);
        Transaction initialTransaction = new Transaction(null, 100L, 101L, null, null, null, null, null, null, null, null, null);
        Transaction exchangedTransaction = new Transaction(null, 100L, 101L, null, null, null, null, null, null, null, null, null);
        Transaction finalTransaction = new Transaction(1L, 100L, 101L, null, null, null, null, null, null, null, null, null);

        when(transactionFactory.factory(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(initialTransaction);
        when(exchangeRatePurchase.purchase(initialTransaction)).thenReturn(exchangedTransaction);
        when(transactionCase.transaction(exchangedTransaction)).thenReturn(finalTransaction);
        doNothing().when(kafkaTransactionProducer).request(finalTransaction);

        ResponseEntity response = transactionController.payment(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(finalTransaction, response.getBody());
        verify(kafkaTransactionProducer).request(finalTransaction);
    }

    @Test
    void paymentShouldReturnBadRequestOnTransactionException() {
        TransactionDto dto = new TransactionDto(100L, 101L, BigDecimal.valueOf(50.0), "BRL", "Test", TransactionType.TRANSFER, PaymentMethod.PIX);
        Transaction initialTransaction = new Transaction(null, 100L, 101L, null, null, null, null, null, null, null, null, null);

        when(transactionFactory.factory(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(initialTransaction);
        when(exchangeRatePurchase.purchase(initialTransaction)).thenReturn(initialTransaction);
        when(transactionCase.transaction(initialTransaction)).thenThrow(new TransactionException("Invalid account"));

        ResponseEntity response = transactionController.payment(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid account", response.getBody());
        verify(kafkaTransactionProducer, never()).request(any());
    }

    @Test
    void externalPaymentShouldReturnOkWhenTransactionIsSuccessful() {
        TransactionDto dto = new TransactionDto(200L, null, BigDecimal.valueOf(150.0), "USD", "External", TransactionType.DEPOSIT, PaymentMethod.CREDIT_CARD);
        Transaction initialTransaction = new Transaction(null, 200L, null, null, null, null, null, null, null, null, null, null);
        Transaction exchangedTransaction = new Transaction(null, 200L, null, null, null, null, null, null, null, null, null, null);
        Transaction finalTransaction = new Transaction(2L, 200L, null, null, null, null, null, null, null, null, null, null);

        when(transactionFactory.factory(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(initialTransaction);
        when(exchangeRatePurchase.purchase(initialTransaction)).thenReturn(exchangedTransaction);
        when(transactionCase.transactionExternal(exchangedTransaction)).thenReturn(finalTransaction);

        ResponseEntity response = transactionController.externalPayment(dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(finalTransaction, response.getBody());
        verify(transactionCase).transactionExternal(exchangedTransaction);
        verify(kafkaTransactionProducer, never()).request(any());
    }

    @Test
    void externalPaymentShouldReturnBadRequestOnTransactionException() {
        TransactionDto dto = new TransactionDto(200L, null, BigDecimal.valueOf(150.0), "USD", "External", TransactionType.DEPOSIT, PaymentMethod.CREDIT_CARD);
        Transaction initialTransaction = new Transaction(null, 200L, null, null, null, null, null, null, null, null, null, null);

        when(transactionFactory.factory(any(), any(), any(), any(), any(), any(), any(), any())).thenReturn(initialTransaction);
        when(exchangeRatePurchase.purchase(initialTransaction)).thenReturn(initialTransaction);
        when(transactionCase.transactionExternal(initialTransaction)).thenThrow(new TransactionException("External transaction failed"));

        ResponseEntity response = transactionController.externalPayment(dto);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("External transaction failed", response.getBody());
    }

    @Test
    void cancelShouldReturnOkWhenCancellationIsSuccessful() {
        Long transactionId = 1L;
        Transaction cancelledTransaction = new Transaction(transactionId, null, null, null, null, null, null, null, null, null, null, null);

        when(cancelTransaction.cancel(transactionId)).thenReturn(cancelledTransaction);
        doNothing().when(kafkaCancelTransactionProducer).cancel(cancelledTransaction);

        ResponseEntity response = transactionController.cancel(transactionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(cancelledTransaction, response.getBody());
        verify(kafkaCancelTransactionProducer).cancel(cancelledTransaction);
    }

    @Test
    void cancelShouldReturnBadRequestOnTransactionException() {
        Long transactionId = 1L;
        when(cancelTransaction.cancel(transactionId)).thenThrow(new TransactionException("Already cancelled"));

        ResponseEntity response = transactionController.cancel(transactionId);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Already cancelled", response.getBody());
        verify(kafkaCancelTransactionProducer, never()).cancel(any());
    }

    @Test
    void listTransactionShouldReturnOkWithListOfTransactions() {
        Long clientId = 1L;
        int day = 1;
        List<Transaction> transactionList = Collections.singletonList(
                new Transaction(10L, clientId, 101L, null, null, null, null, null, null, null, null, null)
        );

        when(listTransactions.byClientId(clientId, day)).thenReturn(transactionList);

        ResponseEntity response = transactionController.listTransaction(clientId, day);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(transactionList, response.getBody());
    }

    @Test
    void listTransactionShouldReturnBadRequestOnClientNotExistsException() {
        Long clientId = 99L;
        int day = 1;
        when(listTransactions.byClientId(clientId, day)).thenThrow(new ClientNotExistsException("Client not found"));

        ResponseEntity response = transactionController.listTransaction(clientId, day);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Client not found", response.getBody());
    }

    @Test
    void listTransactionShouldReturnBadRequestOnTransactionException() {
        Long clientId = 1L;
        int day = 1;
        when(listTransactions.byClientId(clientId, day)).thenThrow(new TransactionException("Error listing transactions"));

        ResponseEntity response = transactionController.listTransaction(clientId, day);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error listing transactions", response.getBody());
    }

    @Test
    void listTransactionPdfShouldReturnOkWhenPdfIsCreated() {
        Long clientId = 1L;
        int day = 1;
        doNothing().when(pdf).create(clientId, day);

        ResponseEntity response = transactionController.listTransactionPdf(clientId, day);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(pdf).create(clientId, day);
    }

    @Test
    void listTransactionPdfShouldReturnBadRequestOnPdfGeneratorException() {
        Long clientId = 1L;
        int day = 1;
        doThrow(new PdfGeneratorException("PDF generation failed")).when(pdf).create(clientId, day);

        ResponseEntity response = transactionController.listTransactionPdf(clientId, day);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("PDF generation failed", response.getBody());
    }
}