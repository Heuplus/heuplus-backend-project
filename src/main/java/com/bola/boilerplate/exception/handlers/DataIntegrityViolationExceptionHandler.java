package com.bola.boilerplate.exception.handlers;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
/*
    Handles DataIntegrityViolationException thrown by the application
 */
public class DataIntegrityViolationExceptionHandler {
    /*
        Handles duplicate entry exceptions
     */
    @ExceptionHandler
    public ResponseEntity<Map<String, String>> handleDuplicateExceptions(DataIntegrityViolationException ex) {
        HashMap<String, String> error = new HashMap<>();
        String key = "message";
        String message = "Credentials are already taken";
        error.put(key, message);
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
