package com.nttdata.domain.exception;

public class ClientNotExistsException extends RuntimeException {
    public ClientNotExistsException(String message) {
        super(message);
    }
}
