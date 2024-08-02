package com.bola.boilerplate.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthenticationExceptionHandler {
        @ExceptionHandler(AuthenticationException.class)
        public ResponseEntity<Map<String, String>> handleValidationExceptions(AuthenticationException ex) {
            HashMap<String, String> error = new HashMap<>();
            String key = "msg";
            String message = "Authentication failed for given credentials";
            error.put(key, message);
            return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
        }
}
