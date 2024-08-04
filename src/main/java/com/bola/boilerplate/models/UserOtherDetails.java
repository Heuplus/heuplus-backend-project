package com.bola.boilerplate.models;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;
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
@Table(name = "_user_details")
public class UserOtherDetails {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String firstName;
  private String lastName;
  private Date dateOfBirth;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private String phoneNumber;
  private String profilePhotoUrl;
  @CreationTimestamp private LocalDateTime createdAt;
  @UpdateTimestamp private LocalDateTime updatedAt;
  private LocalDateTime deletedAt;

  @OneToOne(mappedBy = "userOtherDetails")
  private User user;
}
