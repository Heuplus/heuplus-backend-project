package com.bola.boilerplate.repository;

import com.bola.boilerplate.dto.ProcedureDto;
import com.bola.boilerplate.models.Procedure;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/*
   ProcedureRepository is data access layer for Procedure entity
*/
public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {
  /*
   Implementation blueprint for getting a Procedure's details without relations
  */
  @Query(
      "SELECT new com.bola.boilerplate.dto.ProcedureDto(id, name, price, createdAt, updatedAt) FROM"
          + " Procedure WHERE id = :id")
  Optional<ProcedureDto> findByIdWithoutUser(@Param("id") UUID id);
}
