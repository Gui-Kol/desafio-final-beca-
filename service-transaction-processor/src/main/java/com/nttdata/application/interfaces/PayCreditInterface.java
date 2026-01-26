package com.nttdata.application.interfaces;

import com.nttdata.domain.transaction.Transaction;

public interface PayCreditInterface {
    void purchaseTransfer(Transaction transaction);

    void setFaild(Transaction transaction);

}
