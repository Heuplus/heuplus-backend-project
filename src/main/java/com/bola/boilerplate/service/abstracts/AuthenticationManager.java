package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.payload.request.AuthenticationRequest;
import com.bola.boilerplate.payload.request.RegisterRequest;
import com.bola.boilerplate.payload.response.AuthenticationResponse;

public interface AuthenticationManager {
  public AuthenticationResponse register(RegisterRequest request);

  public AuthenticationResponse authenticate(AuthenticationRequest request);
}
