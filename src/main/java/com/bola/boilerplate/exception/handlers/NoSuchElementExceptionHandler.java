package com.bola.boilerplate.exception.handlers;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/*
   Handles NoSuchElementExceptions thrown by the application
*/
@ControllerAdvice
public class NoSuchElementExceptionHandler {
  /*
     Handles the NoSuchElementException  by putting the error message
     inside a ResponseEntity with 404 error code
  */
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(NoSuchElementException ex) {
    HashMap<String, String> error = new HashMap<>();
    String key = "message";
    String message = "Not Found";
    error.put(key, message);
    return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
  }
}
