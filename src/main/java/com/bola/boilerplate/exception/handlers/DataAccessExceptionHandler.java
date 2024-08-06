package com.bola.boilerplate.exception.handlers;

import com.bola.boilerplate.payload.response.ResultWithData;
import java.util.HashMap;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 Handles the DataAccessException thrown by the application
*/
@RestControllerAdvice
public class DataAccessExceptionHandler {
  /*
     Handles the DataAccessException  by putting the error message
     inside a ResponseEntity with 500 error code
  */
  @ExceptionHandler(JDBCConnectionException.class)
  ResponseEntity<ResultWithData<Object>> handleDataAccessException(JDBCConnectionException ex) {
    HashMap<String, String> error = new HashMap<>();
    String key = "error";
    String message = "Something went wrong";
    error.put(key, message);
    var result =
        ResultWithData.builder()
            .data(error)
            .message(message)
            .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
            .build();
    return ResponseEntity.ok(result);
  }
}
