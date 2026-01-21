package com.nttdata.domain.exceptions;

public class UpdateFailException extends RuntimeException {
    public UpdateFailException(String message) {
        super(message);
    }
}
