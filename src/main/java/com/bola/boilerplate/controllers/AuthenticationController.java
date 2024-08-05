package com.bola.boilerplate.controllers;

import com.bola.boilerplate.payload.request.AuthenticationRequest;
import com.bola.boilerplate.payload.request.RegisterRequest;
import com.bola.boilerplate.payload.response.AuthenticationResponse;
import com.bola.boilerplate.service.abstracts.AuthenticationManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
/*
  Presentation layer for authentication related operations
 */
public class AuthenticationController {

  private final AuthenticationManager service;

  /*
    Registration handler for users
   */
  @PostMapping("/register")
  @Operation(
      summary = "Registration handler for users",
      description = "Upon success creates a new account with USER role")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Successfully registered an account"),
        @ApiResponse(responseCode = "400", description = "Validation failed for at least one field")
      })
  public ResponseEntity<AuthenticationResponse> register(
      @Valid @RequestBody RegisterRequest request) {
    return ResponseEntity.ok(service.register(request));
  }
  /*
    Login handler for users
   */

  @PostMapping("/authenticate")
  @Operation(
      summary = "Login handler for users",
      description = "By taking credentials authenticates the user")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Authentication completed successfully and returns token"),
        @ApiResponse(
            responseCode = "400",
            description = "Validation failed for at least one field"),
        @ApiResponse(
            responseCode = "403",
            description = "Authentication failed for given crendetials")
      })
  public ResponseEntity<AuthenticationResponse> authenticate(
      @Valid @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }
}
