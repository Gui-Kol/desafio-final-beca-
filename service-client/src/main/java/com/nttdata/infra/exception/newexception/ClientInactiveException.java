package com.nttdata.infra.exception.newexception;

public class ClientInactiveException extends RuntimeException {
    public ClientInactiveException(String message) {
        super(message);
    }
}
