package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.dto.PhysicianDto;
import com.bola.boilerplate.dto.PhysicianSelfDto;
import com.bola.boilerplate.models.Physician;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/*
 Implementation blueprint for PhysicianService
*/
public interface PhysicianManager {
  /*
   Implementation blueprint for creating a new Physician in database
  */
  CreateResponse create(String email, CreatePhysicianRequest createPhysicianRequest);

  /*
   Implementation blueprint for getting a physician's details from database with ID
  */
  PhysicianDto getPhysicianDetails(UUID id);

  /*
   Implementation blueprint for getting a physician's details from database with email address
  */

  PhysicianSelfDto getPhysicianDetails(String email);

  /*
   Implementation blueprint for getting a list of physicians
  */
  Page<PhysicianDto> listPhysicians(Pageable pageable);

  /*
   Implementation blueprint for getting a physician by ID
  */
  Physician getPhysicianByUser(User user);
}
