package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.models.Role;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.models.UserOtherDetails;
import com.bola.boilerplate.payload.request.AuthenticationRequest;
import com.bola.boilerplate.payload.request.RegisterRequest;
import com.bola.boilerplate.payload.response.AuthenticationResponse;
import com.bola.boilerplate.repository.UserRepository;
import com.bola.boilerplate.security.JwtService;
import com.bola.boilerplate.service.abstracts.UserOtherDetailsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService
    implements com.bola.boilerplate.service.abstracts.AuthenticationManager {
  private final UserRepository repository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  private final UserOtherDetailsManager userOtherDetailsService;

  @Override
  public AuthenticationResponse register(RegisterRequest request) {
    var user =
        User.builder()
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(Role.ROLE_USER)
            .build();
    User created = repository.save(user);
    // save details
    UserOtherDetails detailsToCreate =
        UserOtherDetails.builder()
            .firstName(request.getFirstName())
            .lastName(request.getLastName())
            .dateOfBirth(request.getDateOfBirth())
            .gender(request.getGender())
            .build();
    UserOtherDetails detailsCreated = userOtherDetailsService.create(detailsToCreate);
    created.setUserOtherDetails(detailsCreated);
    repository.save(created);
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }

  @Override
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
    var user = repository.findByEmail(request.getEmail()).orElseThrow();
    var jwtToken = jwtService.generateToken(user);
    return AuthenticationResponse.builder().token(jwtToken).build();
  }
}
