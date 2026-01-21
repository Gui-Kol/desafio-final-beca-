package com.nttdata.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Transaction {
    private UUID id;
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


}
