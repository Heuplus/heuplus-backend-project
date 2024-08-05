package com.bola.boilerplate.payload.request;

import com.bola.boilerplate.models.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
/*
  Dto for registration requests
 */
public class RegisterRequest {
  @NotBlank(message = "First Name field cannot be blank")
  private String firstName;

  @NotBlank(message = "Last Name field cannot be blank")
  private String lastName;

  @NotBlank(message = "Email field cannot be blank")
  @Pattern(
      regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
      message = "Wrong email format")
  private String email;

  @NotBlank(message = "Password field cannot be blank")
  @Pattern(
      regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).{8,}$",
      message =
          "Password must consists of 1 uppercase letter, 1 lowercase letter, 1 numeric character"
              + " and at least 8 characters long")
  private String password;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date dateOfBirth;

  @Enumerated(EnumType.STRING)
  private Gender gender;
}
