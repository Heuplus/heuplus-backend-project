package com.bola.boilerplate.exception.handlers;

import com.bola.boilerplate.exception.exceptions.RoleChangeNotPossibleException;
import java.util.HashMap;
import java.util.Map;

import com.bola.boilerplate.payload.response.ResultWithData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
/*
 Handles exceptions when a new RoleChangeNotPossible Custom Exception thrown
*/
public class RoleChangeNotPossibleExceptionHandler {
  /*
     Handles the RoleChangeNotPossibleException  by putting the error message
     inside a ResponseEntity with 409 error code
  */
  @ExceptionHandler(RoleChangeNotPossibleException.class)
  public ResponseEntity<ResultWithData<Object>> handleValidationExceptions(
      RoleChangeNotPossibleException ex) {
    HashMap<String, String> error = new HashMap<>();
    String key = "error";
    error.put(key, ex.getMessage());
    var result = ResultWithData.builder()
            .data(error)
            .message(ex.getMessage())
            .statusCode(HttpStatus.CONFLICT.value())
            .build();
    return ResponseEntity.ok(result);
  }
}
