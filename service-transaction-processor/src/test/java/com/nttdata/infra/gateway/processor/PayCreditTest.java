package com.nttdata.infra.gateway.processor;

import com.nttdata.application.usecases.processor.Completed;
import com.nttdata.application.usecases.processor.Faild;
import com.nttdata.domain.bank.Bank;
import com.nttdata.domain.bank.BankDto;
import com.nttdata.domain.transaction.Transaction;
import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;
import com.nttdata.infra.service.MockApiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayCreditTest {
    @Mock
    private Faild faild;

    @Mock
    private Completed completed;

    @Mock
    private MockApiService mockApiService;

    @InjectMocks
    private PayCredit payCredit;

    @Captor
    private ArgumentCaptor<Bank> bankCaptor;

    private String url;

    @BeforeEach
    void setUp() {
        url = "http://6973d075b5f46f8b5828534e.mockapi.io/mockapi/bank/";
    }

    @Test
    void purchaseTransfer_SufficientCredit_ShouldCompleteTransaction() {
        Transaction transaction = new Transaction(1L, 100L, 200L, null, "USD", null, new BigDecimal("250.00"), "Credit purchase", LocalDateTime.now(), StatusTransaction.PENDING, TransactionType.PURCHASE, PaymentMethod.CREDIT_CARD);
        BankDto sourceBankDto = new BankDto(1L, 1L, 1000, 500.0, 100L);
        BankDto destinationBankDto = new BankDto(2L, 2L, 2000.0, 0.0, 200L);

        when(mockApiService.get(100L)).thenReturn(sourceBankDto);
        when(mockApiService.get(200L)).thenReturn(destinationBankDto);

        payCredit.purchaseTransfer(transaction);

        verify(mockApiService, times(2)).put(eq(url), bankCaptor.capture());
        verify(completed).complete(transaction);
        verify(faild, never()).fail(any(Transaction.class));

        List<Bank> capturedBanks = bankCaptor.getAllValues();
        Bank updatedSource = capturedBanks.get(0);
        Bank updatedDestination = capturedBanks.get(1);

        assertEquals(1000.0, updatedSource.getBalance());
        assertEquals(250.0, updatedSource.getCredit());
        assertEquals(2250.0, updatedDestination.getBalance());
        assertEquals(0.0, updatedDestination.getCredit());
    }

    @Test
    void purchaseTransfer_InsufficientCredit_ShouldFailTransaction() {
        Transaction transaction = new Transaction(1L, 100L, 200L, null, "USD", null, new BigDecimal("600.00"), "Credit purchase", LocalDateTime.now(), StatusTransaction.PENDING, TransactionType.PURCHASE, PaymentMethod.CREDIT_CARD);
        BankDto sourceBankDto = new BankDto(1L, 1L, 1000.0, 500.0, 100L);
        BankDto destinationBankDto = new BankDto(2L, 2L, 2000.0, 0.0, 200L);

        when(mockApiService.get(100L)).thenReturn(sourceBankDto);
        when(mockApiService.get(200L)).thenReturn(destinationBankDto);

        payCredit.purchaseTransfer(transaction);

        verify(faild).fail(transaction);
        verify(completed, never()).complete(any(Transaction.class));
        verify(mockApiService, never()).put(anyString(), any(Bank.class));
    }

    @Test
    void setFaild_ShouldCallFaildFail() {
        Transaction transaction = new Transaction(2L, null, null, null, null, null, null, null, null, null, null, null);
        payCredit.setFaild(transaction);
        verify(faild).fail(transaction);
    }
}