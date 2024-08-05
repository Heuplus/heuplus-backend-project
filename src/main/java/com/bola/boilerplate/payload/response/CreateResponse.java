package com.bola.boilerplate.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
/*
  Dto for creation responses
 */
public class CreateResponse {
  private String message;
}
