package com.bola.boilerplate.payload.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*
    Dto for creating a new procedure account requests
*/
public class CreateProcedureRequest {
  @NotBlank(message = "Name field cannot be blank")
  private String name;
  @Positive(message = "Price field cannot be negative")
  private Double price;
}
