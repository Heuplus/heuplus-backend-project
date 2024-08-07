package com.bola.boilerplate.dto;

import com.bola.boilerplate.models.Gender;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
/*
   PhysicianDto holds data transfer fields for Physician entity for physician's self
*/
public class PhysicianSelfDto {
  private UUID physicianId;
  private String qualifications;
  private String specialization;
  private String description;
  private String educationRecord;
  private String previousExperience;
  private String profilePhotoUrl;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private Gender gender;
  private String email;
  private Date dateOfBirth;
}
