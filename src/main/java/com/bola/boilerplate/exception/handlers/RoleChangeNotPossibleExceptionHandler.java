package com.bola.boilerplate.exception.handlers;

import com.bola.boilerplate.exception.exceptions.RoleChangeNotPossibleException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RoleChangeNotPossibleExceptionHandler {
    @ExceptionHandler(RoleChangeNotPossibleException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(RoleChangeNotPossibleException ex) {
        HashMap<String, String> error = new HashMap<>();
        String key = "msg";
        error.put(key, ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }
}
