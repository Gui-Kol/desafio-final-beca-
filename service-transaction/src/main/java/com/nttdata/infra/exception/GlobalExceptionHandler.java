package com.nttdata.infra.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidationErrors(MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(e.getDetailMessageArguments());
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity handleJwtErrors(JwtException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

}
