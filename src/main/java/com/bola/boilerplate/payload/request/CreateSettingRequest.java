package com.bola.boilerplate.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*
    Dto for creating a new setting requests
*/
public class CreateSettingRequest {
  @NotBlank(message = "Key field cannot be blank")
  private String key;
  @NotBlank(message = "Value field cannot be blank")
  private String value;
}
