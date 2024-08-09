package com.bola.boilerplate.exception.handlers;

import com.bola.boilerplate.exception.exceptions.MandatoryArgumentMissingException;
import com.bola.boilerplate.payload.response.ResultWithData;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
/*
   Handles exceptions related to validation
*/
public class ValidationExceptionHandler {

  /*
     Handles the MethodArgumentNotValidExceptions by putting the error messages in to a list and returning them
     inside a ResponseEntity with 400 error code
  */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResultWithData<Object>> handleValidationExceptions(
      MethodArgumentNotValidException ex) {
    Map<String, String> errors = new HashMap<>();
    StringBuilder combinedMessage = new StringBuilder();
    ex.getBindingResult()
        .getFieldErrors()
        .forEach(
            (error) -> {
              String fieldName = error.getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
              combinedMessage.append(error.getDefaultMessage()).append("\n");
            });

    var result =
        ResultWithData.builder()
            .data(errors)
            .message(combinedMessage.toString())
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .build();

    return ResponseEntity.ok(result);
  }

  /*
     Handles the MandatoryArgumentMissingException by putting the error messages in to a list and returning them
     inside a ResponseEntity with 400 error code
  */
  @ExceptionHandler(MandatoryArgumentMissingException.class)
  public ResponseEntity<ResultWithData<Object>> handleMandatoryParameterMissingException(
      MandatoryArgumentMissingException ex) {
    Map<String, String> errors = new HashMap<>();
    errors.put("error", ex.getMessage());
    var result =
        ResultWithData.builder()
            .message(ex.getMessage())
            .data(errors)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .build();
    return ResponseEntity.ok(result);
  }

  /*
     Handles the MissingServletRequestParameterException by putting the error messages in to a list and returning them
     inside a ResponseEntity with 400 error code
  */
  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<ResultWithData<Object>> handleMissingServletRequestParameterException(
      MissingServletRequestParameterException ex) {
    Map<String, String> errors = new HashMap<>();
    String message = "Missing mandatory parameter " + ex.getParameterName();
    errors.put("error", message);
    var result =
        ResultWithData.builder()
            .message(message)
            .data(errors)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .build();
    return ResponseEntity.ok(result);
  }
}
