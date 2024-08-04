package com.bola.boilerplate.controllers;

import com.bola.boilerplate.dto.PatientDto;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreatePatientRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.service.abstracts.PatientManager;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {
  private final PatientManager service;

  @PostMapping
  ResponseEntity<CreateResponse> create(
      @AuthenticationPrincipal User user, @Valid @RequestBody CreatePatientRequest request) {
    var response = service.create(user, request);
    return ResponseEntity.ok(response);
  }

  @GetMapping
  @PreAuthorize("hasRole('ROLE_PATIENT')")
  ResponseEntity<PatientDto> details(@AuthenticationPrincipal User user) {
    PatientDto patient = service.details(user);
    return ResponseEntity.ok(patient);
  }
}
