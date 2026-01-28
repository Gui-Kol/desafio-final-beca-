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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PayBalanceTest {

    @Mock
    private Faild faild;

    @Mock
    private Completed completed;

    @Mock
    private MockApiService mockApiService;

    @InjectMocks
    private PayBalance payBalance;

    @Captor
    private ArgumentCaptor<Bank> bankCaptor;

    private String url;

    @BeforeEach
    void setUp() {
        url = "http://6973d075b5f46f8b5828534e.mockapi.io/mockapi/bank/";
    }

    @Test
    void purchaseTransfer_SufficientBalance_ShouldCompleteTransaction() {
        Transaction transaction = new Transaction(1L, 100L, 200L, new BigDecimal(150), "BRL", new BigDecimal(1), new BigDecimal(150), "Test", LocalDateTime.now(), StatusTransaction.PENDING, TransactionType.TRANSFER, PaymentMethod.PIX);
        BankDto sourceBankDto = new BankDto(1L, 1L, 200.0, 0.0, 100L);
        BankDto destinationBankDto = new BankDto(2L, 2L, 500.0, 0.0, 200L);

        when(mockApiService.get(100L)).thenReturn(sourceBankDto);
        when(mockApiService.get(200L)).thenReturn(destinationBankDto);

        payBalance.purchaseTransfer(transaction);

        verify(mockApiService, times(2)).put(eq(url), bankCaptor.capture());
        verify(completed).complete(transaction);
        verify(faild, never()).fail(any(Transaction.class));

        List<Bank> capturedBanks = bankCaptor.getAllValues();
        assertEquals(50.0, capturedBanks.get(0).getBalance());
        assertEquals(650.0, capturedBanks.get(1).getBalance());
    }

    @Test
    void purchaseTransfer_InsufficientBalance_ShouldFailTransaction() {
        Transaction transaction = new Transaction(1L, 100L, 200L, new BigDecimal(300), "BRL", null, new BigDecimal(300), "Test", LocalDateTime.now(), StatusTransaction.PENDING, TransactionType.TRANSFER, PaymentMethod.PIX);
        BankDto sourceBankDto = new BankDto(1L, 1L, 200.0, 0.0, 100L);
        BankDto destinationBankDto = new BankDto(2L, 2L, 500.0, 0.0, 200L);

        when(mockApiService.get(100L)).thenReturn(sourceBankDto);
        when(mockApiService.get(200L)).thenReturn(destinationBankDto);

        payBalance.purchaseTransfer(transaction);

        verify(faild).fail(transaction);
        verify(completed, never()).complete(any(Transaction.class));
        verify(mockApiService, never()).put(anyString(), any(Bank.class));
    }

    @Test
    void withdrawal_SufficientBalance_ShouldCompleteTransaction() {
        Transaction transaction = new Transaction(1L, 100L, 200L, new BigDecimal(100), "BRL", null, new BigDecimal(100), "Test", LocalDateTime.now(), StatusTransaction.PENDING, TransactionType.TRANSFER, PaymentMethod.PIX);
        BankDto sourceBankDto = new BankDto(1L, 1L, 200.0, 0.0, 100L);


        when(mockApiService.get(100L)).thenReturn(sourceBankDto);

        payBalance.withdrawal(transaction);

        verify(mockApiService).put(eq(url), bankCaptor.capture());
        verify(completed).complete(transaction);
        verify(faild, never()).fail(any(Transaction.class));

        assertEquals(100.0, bankCaptor.getValue().getBalance());
    }

    @Test
    void withdrawal_InsufficientBalance_ShouldFailTransaction() {
        Transaction transaction = new Transaction(1L, 100L, 200L, new BigDecimal(150), "BRL", null, new BigDecimal(150), "Test", LocalDateTime.now(), StatusTransaction.PENDING, TransactionType.TRANSFER, PaymentMethod.PIX);
        BankDto sourceBankDto = new BankDto(1L, 1L, 100, 0.0, 100L);

        when(mockApiService.get(100L)).thenReturn(sourceBankDto);

        payBalance.withdrawal(transaction);

        verify(faild).fail(transaction);
        verify(completed, never()).complete(any(Transaction.class));
        verify(mockApiService, never()).put(anyString(), any(Bank.class));
    }

    @Test
    void deposit_ConditionMet_ShouldCompleteTransaction() {
        Transaction transaction = new Transaction(1L, 100L, 200L, new BigDecimal(150), "BRL", null, new BigDecimal(150), "Test", LocalDateTime.now(), StatusTransaction.PENDING, TransactionType.TRANSFER, PaymentMethod.PIX);
        BankDto sourceBankDto = new BankDto(1L, 1L, 150, 0.0, 100L);

        when(mockApiService.get(100L)).thenReturn(sourceBankDto);

        payBalance.deposit(transaction);

        verify(mockApiService).put(eq(url), bankCaptor.capture());
        verify(completed).complete(transaction);
        verify(faild, never()).fail(any(Transaction.class));

        assertEquals(300.0, bankCaptor.getValue().getBalance());
    }

    @Test
    void deposit_ConditionNotMet_ShouldFailTransaction() {
        Transaction transaction = new Transaction(1L, 100L, 200L, new BigDecimal(300), "BRL", null, new BigDecimal(300), "Test", LocalDateTime.now(), StatusTransaction.PENDING, TransactionType.TRANSFER, PaymentMethod.PIX);
        BankDto sourceBankDto = new BankDto(1L, 1L, 200.0, 0.0, 100L);

        when(mockApiService.get(100L)).thenReturn(sourceBankDto);

        payBalance.deposit(transaction);

        verify(faild).fail(transaction);
        verify(completed, never()).complete(any(Transaction.class));
        verify(mockApiService, never()).put(anyString(), any(Bank.class));
    }

    @Test
    void setFaild_ShouldCallFaildFail() {
        Transaction transaction = new Transaction(4L, null, null, null, null, null, null, null, null, null, null, null);
        payBalance.setFaild(transaction);
        verify(faild).fail(transaction);
    }

    @Test
    void setCompleted_ShouldCallCompletedComplete() {
        Transaction transaction = new Transaction(5L, null, null, null, null, null, null, null, null, null, null, null);
        payBalance.setCompleted(transaction);
        verify(completed).complete(transaction);
    }
}