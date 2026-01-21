package com.nttdata.infra.exceptions;

public class UpdateFailException extends RuntimeException {
    public UpdateFailException(String message) {
        super(message);
    }
}
