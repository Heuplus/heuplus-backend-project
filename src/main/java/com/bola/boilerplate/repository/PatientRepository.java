package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PatientRepository extends JpaRepository<Patient, UUID> {
}
