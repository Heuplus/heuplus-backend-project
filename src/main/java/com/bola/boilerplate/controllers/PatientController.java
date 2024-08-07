package com.bola.boilerplate.controllers;

import com.bola.boilerplate.dto.PatientDto;
import com.bola.boilerplate.payload.request.CreatePatientRequest;
import com.bola.boilerplate.payload.response.ResultWithData;
import com.bola.boilerplate.service.abstracts.PatientManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
/*
 Presentation layer for patient related operations
*/
public class PatientController {
  private final PatientManager service;

  /*
   Converts a USER account to PATIENT
  */
  @PostMapping
  @Operation(
      summary = "Converts a USER account to PATIENT",
      description =
          "Converting an account's role from temporary USER to PATIENT with adding other details"
              + " for the patient")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Converted USER account to PATIENT account successfully"),
        @ApiResponse(responseCode = "409", description = "Role change is not possible"),
        @ApiResponse(responseCode = "403", description = "Not authorized for the action")
      })
  public ResponseEntity<ResultWithData<Object>> create(
      @AuthenticationPrincipal UserDetails userDetails,
      @Valid @RequestBody CreatePatientRequest request) {
    var response = service.create(userDetails.getUsername(), request);
    var result =
        ResultWithData.builder()
            .data(response)
            .message("Converted USER account to PATIENT account successfully")
            .statusCode(HttpStatus.CREATED.value())
            .build();
    return ResponseEntity.ok(result);
  }

  /*
   Gets a Patient's details
  */
  @GetMapping
  @PreAuthorize("hasRole('ROLE_PATIENT')")
  @Operation(
      summary = "Gets a Patient's details",
      description = "Using the authentication context gets a patient's details")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Get the details of the Patient's successfully"),
        @ApiResponse(responseCode = "403", description = "Unauthorized for the action")
      })
  public ResponseEntity<ResultWithData<Object>> details(@AuthenticationPrincipal UserDetails userDetails) {
    PatientDto patient = service.details(userDetails.getUsername());
    var result =
        ResultWithData.builder()
            .statusCode(HttpStatus.OK.value())
            .message("Got the Patient's details")
            .data(patient)
            .build();
    return ResponseEntity.ok(result);
  }
}
