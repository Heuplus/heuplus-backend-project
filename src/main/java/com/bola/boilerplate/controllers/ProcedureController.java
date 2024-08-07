package com.bola.boilerplate.controllers;

import com.bola.boilerplate.payload.request.CreateProcedureRequest;
import com.bola.boilerplate.payload.response.ResultWithData;
import com.bola.boilerplate.service.abstracts.ProcedureManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/procedures")
@RequiredArgsConstructor
/*
 Presentation layer for procedure related operations
*/
public class ProcedureController {
  private final ProcedureManager service;

  /*
      Creates a new procedure for the authorized physician
  */
  @PostMapping
  @PreAuthorize("hasRole('ROLE_PHYSICIAN')")
  @Operation(
      summary = "Creates a new procedure",
      description = "Creating a new procedure for the authorized physician")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Created procedure successfully"),
        @ApiResponse(responseCode = "403", description = "Not authorized for the action")
      })
  public ResponseEntity<ResultWithData<Object>> create(
      @RequestBody @Valid CreateProcedureRequest createProcedureRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    var result =
        ResultWithData.builder()
            .message("Procedure created successfully")
            .data(service.create(userDetails.getUsername(), createProcedureRequest))
            .statusCode(201)
            .build();
    return ResponseEntity.ok(result);
  }

  /*
   Gets a procedure from database with its details
  */
  @GetMapping("/{procedureId}")
  @PreAuthorize("hasAnyRole('ROLE_PHYSICIAN', 'ROLE_PATIENT')")
  @Operation(summary = "Getting a procedure", description = "Getting a procedure with it's details")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Got procedure successfully"),
        @ApiResponse(responseCode = "403", description = "Not authorized for the action")
      })
  public ResponseEntity<ResultWithData<Object>> details(@PathVariable UUID procedureId) {
    var result =
        ResultWithData.builder()
            .message("Got procedure successfully")
            .data(service.details(procedureId))
            .statusCode(200)
            .build();
    return ResponseEntity.ok(result);
  }
  /*
    Gets procedures of a physician
  */
  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_PHYSICIAN', 'ROLE_PATIENT')")
  @Operation(summary = "Listing procedures of a physician", description = "Listing procedures of a physician")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "Got procedures successfully"),
                  @ApiResponse(responseCode = "403", description = "Not authorized for the action")
          })
  public ResponseEntity<ResultWithData<Object>> list(@RequestParam UUID physicianId, Pageable pageable) {
    var result =
            ResultWithData.builder()
                    .message("Got procedures successfully")
                    .data(service.getPhysiciansProcedures(physicianId, pageable))
                    .statusCode(200)
                    .build();
    return ResponseEntity.ok(result);
  }
}
