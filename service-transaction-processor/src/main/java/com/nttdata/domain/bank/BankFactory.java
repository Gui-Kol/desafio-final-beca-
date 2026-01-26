package com.nttdata.domain.bank;

public class BankFactory {

    public Bank createForId(Long id) {
        return new Bank(id, id, randomNum(),
                randomNum(), 1L);
    }

    private double randomNum() {
        return (int) (Math.random() * 9999) + 1;
    }


}
