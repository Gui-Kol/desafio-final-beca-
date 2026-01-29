package com.nttdata.infra.exception.newexception;

public class ClientNotExistsException extends RuntimeException {
    public ClientNotExistsException(String message) {
        super(message);
    }
}
