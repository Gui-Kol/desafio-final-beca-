package com.nttdata.domain.transaction;

import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private Long id;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal value;
    private String currency; //moeda
    private BigDecimal appliedExchangeRate; //taxa de cambio aplicada
    private BigDecimal convertedValue;
    private String description;
    private LocalDateTime transactionDate;
    private StatusTransaction status;
    private TransactionType type;
    private PaymentMethod method;

    public Transaction(Long id, Long sourceAccountId, Long destinationAccountId, BigDecimal value, String currency, BigDecimal appliedExchangeRate, BigDecimal convertedValue, String description, LocalDateTime transactionDate, StatusTransaction status, TransactionType type, PaymentMethod method) {
        this.id = id;
        this.sourceAccountId = sourceAccountId;
        this.destinationAccountId = destinationAccountId;
        this.value = value;
        this.currency = currency;
        this.appliedExchangeRate = appliedExchangeRate;
        this.convertedValue = convertedValue;
        this.description = description;
        this.transactionDate = transactionDate;
        this.status = status;
        this.type = type;
        this.method = method;
    }

    public Long getId() {
        return id;
    }

    public Long getSourceAccountId() {
        return sourceAccountId;
    }

    public Long getDestinationAccountId() {
        return destinationAccountId;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAppliedExchangeRate() {
        return appliedExchangeRate;
    }

    public BigDecimal getConvertedValue() {
        return convertedValue;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public StatusTransaction getStatus() {
        return status;
    }

    public TransactionType getType() {
        return type;
    }

    public PaymentMethod getMethod() {
        return method;
    }

    public void setStatus(StatusTransaction status) {
        this.status = status;
    }

    public void setConvertedValue(BigDecimal convertedValue) {
        this.convertedValue = convertedValue;
    }

    public void setAppliedExchangeRate(BigDecimal appliedExchangeRate) {
        this.appliedExchangeRate = appliedExchangeRate;
    }
}
