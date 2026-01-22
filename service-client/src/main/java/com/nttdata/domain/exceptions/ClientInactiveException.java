package com.nttdata.domain.exceptions;

public class ClientInactiveException extends RuntimeException {
    public ClientInactiveException(String message) {
        super(message);
    }
}
