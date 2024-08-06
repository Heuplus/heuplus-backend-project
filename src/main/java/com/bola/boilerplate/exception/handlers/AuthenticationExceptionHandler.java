package com.bola.boilerplate.exception.handlers;

import com.bola.boilerplate.payload.response.ResultWithData;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/*
 Handles the AuthenticationExceptions thrown by the application
*/
@RestControllerAdvice
public class AuthenticationExceptionHandler {
  /*
     Handles the AuthenticationException  by putting the error message
     inside a ResponseEntity with 403 error code
  */
  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<ResultWithData<Object>> handleAuthenticationExceptions(
      AuthenticationException ex) {
    HashMap<String, String> error = new HashMap<>();
    String key = "error";
    String message = "Authentication failed for given credentials";
    error.put(key, message);
    var result =
        ResultWithData.builder()
            .data(error)
            .message(message)
            .statusCode(HttpStatus.FORBIDDEN.value())
            .build();
    return ResponseEntity.ok(result);
  }
}
