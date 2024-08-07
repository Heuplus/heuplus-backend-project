package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.Physician;
import com.bola.boilerplate.models.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/*
   PhysicianRepository is data access layer for Physician entity
*/
public interface PhysicianRepository extends JpaRepository<Physician, UUID> {
  Optional<Physician> findByUser(User user);
}
