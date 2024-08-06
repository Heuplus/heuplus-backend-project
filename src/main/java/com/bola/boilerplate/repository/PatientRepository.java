package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.Patient;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/*
   Data access layer implementation for Patient entity
*/
public interface PatientRepository extends JpaRepository<Patient, UUID> {

  /*
   Implementation blueprint for getting a Patient's details by email
  */
  Optional<Patient> findByUserEmail(String email);
}
