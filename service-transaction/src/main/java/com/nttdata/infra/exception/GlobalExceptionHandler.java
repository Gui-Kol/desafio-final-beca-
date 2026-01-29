package com.nttdata.infra.exception;

import com.nttdata.infra.exception.newexception.JwtException;
import jakarta.validation.UnexpectedTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
    public ResponseEntity handleJwtErrors(JwtException e){
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(UnexpectedTypeException.class)
    public ResponseEntity handleValidationErrors(UnexpectedTypeException e){
        return ResponseEntity.badRequest().body(e.getLocalizedMessage());
    }
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity handleValidationErrors(HttpMessageNotReadableException e){
        return ResponseEntity.badRequest().body(e.getMostSpecificCause().getLocalizedMessage());
    }
}
