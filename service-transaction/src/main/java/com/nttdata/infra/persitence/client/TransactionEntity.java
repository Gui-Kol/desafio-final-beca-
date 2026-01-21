package com.nttdata.infra.persitence.client;

import com.nttdata.domain.PaymentMethod;
import com.nttdata.domain.StatusTransaction;
import com.nttdata.domain.TransactionType;
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
    private StatusTransaction status;
    private TransactionType type;
    private PaymentMethod method;

}
