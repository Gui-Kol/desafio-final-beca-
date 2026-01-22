package com.nttdata.infra.persitence.client;

import com.nttdata.domain.transaction.attribute.PaymentMethod;
import com.nttdata.domain.transaction.attribute.StatusTransaction;
import com.nttdata.domain.transaction.attribute.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "transactions")
@Entity(name = "transaction")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long sourceAccountId;
    private Long destinationAccountId;
    private BigDecimal value;
    private String currency; //moeda
    private BigDecimal appliedExchangeRate; //taxa de cambio aplicada
    private BigDecimal convertedValue;
    private String description;
    private LocalDateTime transactionDate;
    @Enumerated(EnumType.STRING)
    private StatusTransaction status;
    @Enumerated(EnumType.STRING)
    private TransactionType type;
    @Enumerated(EnumType.STRING)
    private PaymentMethod method;

    public void cancelTransaction() {
        this.status = StatusTransaction.CANCELED;
    }
}
