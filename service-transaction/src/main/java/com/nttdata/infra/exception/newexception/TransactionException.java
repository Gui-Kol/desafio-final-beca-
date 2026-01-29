package com.nttdata.infra.exception.newexception;

public class TransactionException extends RuntimeException {
    public TransactionException(String message) {
        super(message);
    }
}
