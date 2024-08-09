package com.bola.boilerplate.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*
 Dto for authentication requests
*/
public class AuthenticationRequest {

  @NotBlank(message = "Email field cannot be blank")
  @Pattern(
      regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
      message = "Wrong email format")
  private String email;

  @NotBlank(message = "Password field cannot be blank")
  private String password;
}
