package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.dto.PhysicianDto;
import com.bola.boilerplate.dto.PhysicianSelfDto;
import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import java.util.UUID;

/*
 Implementation blueprint for PhysicianService
*/
public interface PhysicianManager {
  /*
   Implementation blueprint for creating a new Physician in database
  */
  CreateResponse create(String email, CreatePhysicianRequest createPhysicianRequest);

  /*
   Implementation blueprint for getting a patient's details from database with ID
  */
  PhysicianDto getPhysicianDetails(UUID id);

  /*
   Implementation blueprint for getting a patient's details from database with email address
  */

  PhysicianSelfDto getPhysicianDetails(String email);
}
