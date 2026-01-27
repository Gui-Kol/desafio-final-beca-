package com.nttdata.infra.exception.newexception;

public class UpdateFailException extends RuntimeException {
    public UpdateFailException(String message) {
        super(message);
    }
}
