package com.nttdata.domain.bank;

public record BankDto(
        Long account,
        Long clientId,
        double balance,
        double credit,
        Long id
) {
}
