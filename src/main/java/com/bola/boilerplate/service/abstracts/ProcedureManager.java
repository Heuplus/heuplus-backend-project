package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.payload.request.CreateProcedureRequest;
import com.bola.boilerplate.payload.response.CreateResponse;

/*
 Implementation blueprint for ProcedureService
*/
public interface ProcedureManager {
    /*
   Implementation blueprint for creating a new Procedure in database
  */
    CreateResponse create(String email, CreateProcedureRequest createProcedureRequest);
}
