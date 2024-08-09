package com.bola.boilerplate.controllers;

import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.ResultWithData;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

/*
 Presentation layer for physician related operations
*/
@RestController
@RequestMapping("/api/v1/physicians")
@RequiredArgsConstructor
public class PhysicianController {
  private final PhysicianManager service;

  /**
   * Converts a USER account to PHYSICIAN
   * @param userDetails - AuthenticationPrincipal
   * @param createPhysicianRequest - CreatePhysicianRequest
   * @return ResponseEntity<ResultWithData<Object>>
   */
  @PostMapping
  @Operation(
      summary = "Converts a USER account to PHYSICIAN",
      description =
          "Converting an account's role from temporary USER to PHYSICIAN with adding other details"
              + " for the physician")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Converted USER account to PHYSICIAN account successfully"),
        @ApiResponse(responseCode = "409", description = "Role change is not possible"),
        @ApiResponse(responseCode = "403", description = "Not authorized for the action")
      })
  public ResponseEntity<ResultWithData<Object>> create(
      @AuthenticationPrincipal UserDetails userDetails,
      @RequestBody CreatePhysicianRequest createPhysicianRequest) {
    String email = userDetails.getUsername();
    var result =
        ResultWithData.builder()
            .message("Converted USER account to PHYSICIAN account successfully")
            .data(service.create(email, createPhysicianRequest))
            .statusCode(HttpStatus.CREATED.value())
            .build();
    return ResponseEntity.ok(result);
  }

  /**
   * Gets a Physician's details
   * @param physicianId - UUID
   * @param userDetails - AuthenticationPrincipal
   * @return ResponseEntity<ResultWithData<Object>>
   */
  @GetMapping(value = {"/{physicianId}", "/"})
  @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_PHYSICIAN')")
  @Operation(
      summary = "Gets a Physician's details",
      description =
          "Using the authentication context gets a physician's details or gets the physician from"
              + " ID givenas path variable")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Got the details of the Physician's successfully"),
        @ApiResponse(responseCode = "403", description = "Unauthorized for the action"),
        @ApiResponse(responseCode = "403", description = "Not allowed for the action")
      })
  public ResponseEntity<ResultWithData<Object>> details(
      @PathVariable(name = "physicianId", required = false) UUID physicianId,
      @AuthenticationPrincipal UserDetails userDetails) {
    var result = ResultWithData.builder()
            .data(physicianId == null ? service.getPhysicianDetails(userDetails.getUsername()) : service.getPhysicianDetails(physicianId))
            .message("Got the physician's details")
            .statusCode(200)
            .build();
    return ResponseEntity.ok(result);
  }

  /**
   * Gets a list of physicians
   * @param pageable - Pageable
   * @return ResponseEntity<ResultWithData<Object>>
   */
  @Operation(
      summary = "Gets a list of physicians",
      description = "Gets the list of physicians with pagination")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Got the list of the physicians successfully"),
        @ApiResponse(responseCode = "403", description = "Unauthorized for the action"),
        @ApiResponse(responseCode = "403", description = "Not allowed for the action")
      })
  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_PATIENT', 'ROLE_PHYSICIAN')")
  public ResponseEntity<ResultWithData<Object>> list(@NonNull final Pageable pageable) {
    var result =
        ResultWithData.builder()
            .message("Successfully listed the physicians")
            .data(service.listPhysicians(pageable))
            .statusCode(200)
            .build();
    return ResponseEntity.ok(result);
  }
}
