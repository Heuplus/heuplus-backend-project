package com.bola.boilerplate.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

/*
       This is the physician Dto for patients and other doctors
*/
@Data
@Builder
public class PhysicianDto {
  private UUID physicianId;
  private String qualifications;
  private String specialization;
  private String description;
  private String educationRecord;
  private String previousExperience;
  private String profilePhotoUrl;
  private String firstName;
  private String lastName;
}
