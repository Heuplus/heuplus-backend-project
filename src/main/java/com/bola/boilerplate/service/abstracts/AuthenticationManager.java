package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.payload.request.AuthenticationRequest;
import com.bola.boilerplate.payload.request.RegisterRequest;
import com.bola.boilerplate.payload.response.AuthenticationResponse;

/*
 Implementation blueprint for AuthenticationService
*/
public interface AuthenticationManager {
  /*
   Implementation blueprint for registration with creating a new User in database
  */
  AuthenticationResponse register(RegisterRequest request);

  /*
   Implementation blueprint for authenticating a User
  */
  AuthenticationResponse authenticate(AuthenticationRequest request);
}
