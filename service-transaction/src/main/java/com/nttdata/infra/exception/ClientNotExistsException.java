package com.nttdata.infra.exception;

public class ClientNotExistsException extends RuntimeException {
    public ClientNotExistsException(String message) {
        super(message);
    }
}
