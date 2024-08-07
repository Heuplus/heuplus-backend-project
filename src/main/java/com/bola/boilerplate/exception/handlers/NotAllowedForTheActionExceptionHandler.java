package com.bola.boilerplate.exception.handlers;

import com.bola.boilerplate.exception.exceptions.NotAllowedForTheAction;
import com.bola.boilerplate.payload.response.ResultWithData;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

/*
    Handles NotAllowedForTheActionExceptions thrown by the application
 */
@RestControllerAdvice
public class NotAllowedForTheActionExceptionHandler {
    /*
     Handles the NotAllowedForTheAction  by putting the error message
     inside a ResponseEntity with 403 error code
    */
    @ExceptionHandler(NotAllowedForTheAction.class)
    public ResponseEntity<ResultWithData<Object>> handleNotAllowedForTheActionException(
            NotAllowedForTheAction ex) {
        HashMap<String, String> error = new HashMap<>();
        String key = "error";
        String message = "Not allowed for the action";
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
