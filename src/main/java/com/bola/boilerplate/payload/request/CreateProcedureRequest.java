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
    Dto for creating a new procedure account requests
*/
public class CreateProcedureRequest {
  @NotBlank
  private String name;
  @NotBlank
  private Double price;
}
