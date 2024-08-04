package com.bola.boilerplate.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_insurance_information")
public class InsuranceInformation {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String providerName;
  private String policyNumber;

  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;

  private LocalDateTime deletedAt;

  @OneToOne(mappedBy = "insuranceInformation")
  private Patient patient;
}
