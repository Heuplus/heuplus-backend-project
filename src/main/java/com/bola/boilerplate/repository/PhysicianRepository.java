package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.Physician;
import com.bola.boilerplate.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/*
    PhysicianRepository is data access layer for Physician entity
 */
public interface PhysicianRepository extends JpaRepository<Physician, UUID> {
    Optional<Physician> findByUser(User user);
}
