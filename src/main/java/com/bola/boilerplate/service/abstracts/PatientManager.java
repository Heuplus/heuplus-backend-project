package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.dto.PatientDto;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreatePatientRequest;
import com.bola.boilerplate.payload.response.CreateResponse;

public interface PatientManager {
  CreateResponse create(User user, CreatePatientRequest request);

  PatientDto details(User user);
}
