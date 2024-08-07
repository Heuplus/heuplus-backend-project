package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.dto.ProcedureDto;
import com.bola.boilerplate.models.Physician;
import com.bola.boilerplate.models.Procedure;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreateProcedureRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.ProcedureRepository;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import com.bola.boilerplate.service.abstracts.ProcedureManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
/*
 ProcedureManager implementation for handling Patient entity related business logic
*/
public class ProcedureService implements ProcedureManager {
  private final ProcedureRepository repository;
  private final PhysicianManager physicianManager;
  private final UserManager userManager;

  /*
     Handle creation of a new procedure
  */
  @Override
  public CreateResponse create(String email, CreateProcedureRequest createProcedureRequest) {
    User user = userManager.findUserByEmail(email);
    Physician physician = physicianManager.getPhysicianByUser(user);
    Procedure toCreate =
        Procedure.builder()
            .price(createProcedureRequest.getPrice())
            .name(createProcedureRequest.getName())
            .physician(physician)
            .build();
    repository.save(toCreate);
    return CreateResponse.builder().message("Procedure created successfully").build();
  }

  /*
     Handle getting a procedure from database
  */
  @Override
  public ProcedureDto details(UUID id) {
    return repository.findByIdWithoutUser(id).orElseThrow();
  }
}
