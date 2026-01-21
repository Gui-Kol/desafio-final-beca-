package com.nttdata.domain;

public enum PaymentMethod {
    CASH, //Compras em espécie, não afetam um saldo de conta virtual.
    DEBIT_CARD,
    CREDIT_CARD,
    PIX,
    BOLETO,
}
