package com.nttdata.infra.exception.newexception;

public class JwtException extends RuntimeException {
    public JwtException(String message) {
        super(message);
    }
}
