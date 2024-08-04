package com.bola.boilerplate.dto;

import com.bola.boilerplate.models.Gender;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientDto {
  private UUID userId;
  private UUID patientId;
  private String email;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Date dateOfBirth;
  private String firstName;
  private String lastName;
  private String phoneNumber;
  private Gender gender;
  private String profilePhotoUrl;
  private String medications;
  private String medicalHistory;
  private String insurancePolicyNumber;
  private String insuranceProviderName;
}
