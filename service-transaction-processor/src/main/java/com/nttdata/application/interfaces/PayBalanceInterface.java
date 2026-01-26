package com.nttdata.application.interfaces;

import com.nttdata.domain.transaction.Transaction;

public interface PayBalanceInterface {

    void purchaseTransfer(Transaction transaction);

    void withdrawal(Transaction transaction);

    void deposit(Transaction transaction);

    void setFaild(Transaction transaction);

    void setCompleted(Transaction trasaction);
}
