package com.bola.boilerplate.payload.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*
  Dto for authentication responses
 */
public class AuthenticationResponse {

  private String token;
}
