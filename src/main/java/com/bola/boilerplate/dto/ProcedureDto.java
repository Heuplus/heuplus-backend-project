package com.bola.boilerplate.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
/*
   Dto for procedure details
*/
public class ProcedureDto {
  private UUID procedureId;
  private String name;
  private Double price;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
