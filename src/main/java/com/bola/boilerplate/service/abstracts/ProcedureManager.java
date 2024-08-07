package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.dto.ProcedureDto;
import com.bola.boilerplate.payload.request.CreateProcedureRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/*
 Implementation blueprint for ProcedureService
*/
public interface ProcedureManager {
  /*
   Implementation blueprint for creating a new Procedure in database
  */
  CreateResponse create(String email, CreateProcedureRequest createProcedureRequest);

  /*
   Implementation blueprint for getting a Procedure by ID
  */

  ProcedureDto details(UUID id);

  /*
   Implementation blueprint for getting a Physician's procedures
  */
  Page<ProcedureDto> getPhysiciansProcedures(UUID physicianId, Pageable pageable);
}
