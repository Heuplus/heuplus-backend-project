package com.bola.boilerplate.exception.handlers;

import com.bola.boilerplate.payload.response.ResultWithData;
import java.util.HashMap;
import java.util.NoSuchElementException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
   Handles NoSuchElementExceptions thrown by the application
*/
@RestControllerAdvice
public class NoSuchElementExceptionHandler {
  /*
     Handles the NoSuchElementException  by putting the error message
     inside a ResponseEntity with 404 error code
  */
  @ExceptionHandler(NoSuchElementException.class)
  public ResponseEntity<ResultWithData<Object>> handleValidationExceptions(
      NoSuchElementException ex) {
    HashMap<String, String> error = new HashMap<>();
    String key = "error";
    String message = "Not Found";
    error.put(key, message);
    var result =
        ResultWithData.builder()
            .data(error)
            .message(message)
            .statusCode(HttpStatus.NOT_FOUND.value())
            .build();
    return ResponseEntity.ok(result);
  }
}
