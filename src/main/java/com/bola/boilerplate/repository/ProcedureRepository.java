package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

/*
   ProcedureRepository is data access layer for Procedure entity
*/
public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {
}
