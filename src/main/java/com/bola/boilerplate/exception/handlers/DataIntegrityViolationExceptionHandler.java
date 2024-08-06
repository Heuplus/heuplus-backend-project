package com.bola.boilerplate.exception.handlers;

import java.util.HashMap;
import java.util.Map;

import com.bola.boilerplate.payload.response.ResultWithData;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
/*
   Handles DataIntegrityViolationException thrown by the application
*/
public class DataIntegrityViolationExceptionHandler {
  /*
     Handles duplicate entry exceptions
  */
  @ExceptionHandler
  public ResponseEntity<ResultWithData<Object>> handleDuplicateExceptions(
      DataIntegrityViolationException ex) {
    HashMap<String, String> error = new HashMap<>();
    String key = "error";
    String message = "Credentials are already taken";
    error.put(key, message);
    var result = ResultWithData.builder()
            .data(error)
            .message(message)
            .statusCode(HttpStatus.CONFLICT.value())
            .build();
    return ResponseEntity.ok(result);
  }
}
