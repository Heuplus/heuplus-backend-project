package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.Patient;
import com.bola.boilerplate.models.User;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
  Patient findByUserEmail(String email);
}
