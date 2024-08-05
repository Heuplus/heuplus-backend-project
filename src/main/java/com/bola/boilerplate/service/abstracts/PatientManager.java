package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.dto.PatientDto;
import com.bola.boilerplate.payload.request.CreatePatientRequest;
import com.bola.boilerplate.payload.response.CreateResponse;

/*
 Implementation blueprint for PatientService
*/
public interface PatientManager {

  /*
   Implementation blueprint for creating a new Patient in database
  */
  CreateResponse create(String email, CreatePatientRequest request);

  /*
   Implementation blueprint for getting a patient's details from database
  */
  PatientDto details(String email);
}
