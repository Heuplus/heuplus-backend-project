package com.bola.boilerplate.payload.response;

import lombok.Builder;
import lombok.Data;

/*
   ResultWithData is a Generic Response type for the application
*/
@Data
@Builder
public class ResultWithData<T> {
  private String message;
  private T data;
  private Integer statusCode;
}
