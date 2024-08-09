package com.bola.boilerplate.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*
    Dto for creating a new physician account requests
*/
public class CreatePhysicianRequest {
  private String qualifications;
  private String specialization;
  private String description;
  private String educationRecord;
  private String previousExperience;
}
