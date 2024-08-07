package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.Procedure;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/*
   ProcedureRepository is data access layer for Procedure entity
*/
public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {}
