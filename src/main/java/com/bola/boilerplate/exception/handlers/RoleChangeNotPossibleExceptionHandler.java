package com.bola.boilerplate.exception.handlers;

import com.bola.boilerplate.exception.exceptions.RoleChangeNotPossibleException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
/*
  Handles exceptions when a new RoleChangeNotPossible Custom Exception thrown
 */
public class RoleChangeNotPossibleExceptionHandler {
  /*
    Handles the RoleChangeNotPossibleException  by putting the error message
    inside a ResponseEntity with 409 error code
 */
  @ExceptionHandler(RoleChangeNotPossibleException.class)
  public ResponseEntity<Map<String, String>> handleValidationExceptions(
      RoleChangeNotPossibleException ex) {
    HashMap<String, String> error = new HashMap<>();
    String key = "message";
    error.put(key, ex.getMessage());
    return new ResponseEntity<>(error, HttpStatus.CONFLICT);
  }
}
