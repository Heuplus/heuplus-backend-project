package com.bola.boilerplate.controllers;

import com.bola.boilerplate.payload.request.CreateSettingRequest;
import com.bola.boilerplate.payload.response.ResultWithData;
import com.bola.boilerplate.service.abstracts.SettingManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/settings")
@RequiredArgsConstructor
/*
 Presentation layer for setting related operations
*/
public class SettingController {
  private final SettingManager service;

  /*
   Creates a new setting for the authorized physician or patient
  */
  @PostMapping
  @PreAuthorize("hasAnyRole('ROLE_PHYSICIAN', 'ROLE_PATIENT')")
  @Operation(
      summary = "Creates a new setting",
      description = "Creating a new setting for the authorized physician or patient")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "Created setting successfully"),
        @ApiResponse(responseCode = "403", description = "Not authorized for the action")
      })
  public ResponseEntity<ResultWithData<Object>> create(
      @RequestBody @Valid CreateSettingRequest createSettingRequest,
      @AuthenticationPrincipal UserDetails userDetails) {
    var result =
        ResultWithData.builder()
            .message("Setting created successfully")
            .data(service.create(userDetails.getUsername(), createSettingRequest))
            .statusCode(201)
            .build();
    return ResponseEntity.ok(result);
  }

  /*
   Gets a setting from database with its details
  */
  @GetMapping("/{settingId}")
  @PreAuthorize("hasAnyRole('ROLE_PHYSICIAN', 'ROLE_PATIENT')")
  @Operation(summary = "Getting a setting", description = "Getting a setting with it's details")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "Got setting successfully"),
                  @ApiResponse(responseCode = "403", description = "Not authorized for the action"),
                  @ApiResponse(responseCode = "404", description = "Setting not found")
          })
  public ResponseEntity<ResultWithData<Object>> details(@PathVariable UUID settingId, @AuthenticationPrincipal UserDetails userDetails) {
    var result =
            ResultWithData.builder()
                    .message("Got setting successfully")
                    .data(service.details(userDetails.getUsername(), settingId))
                    .statusCode(200)
                    .build();
    return ResponseEntity.ok(result);
  }

  /*
   Gets a list of settings from database with its details
  */
  @GetMapping
  @PreAuthorize("hasAnyRole('ROLE_PHYSICIAN', 'ROLE_PATIENT')")
  @Operation(summary = "Getting a list of settings", description = "Getting a list of settings with it's details")
  @ApiResponses(
          value = {
                  @ApiResponse(responseCode = "200", description = "Got settings successfully"),
                  @ApiResponse(responseCode = "403", description = "Not authorized for the action")
          })
  public ResponseEntity<ResultWithData<Object>> list(@AuthenticationPrincipal UserDetails userDetails) {
    var result =
            ResultWithData.builder()
                    .message("Got settings successfully")
                    .data(service.list(userDetails.getUsername()))
                    .statusCode(200)
                    .build();
    return ResponseEntity.ok(result);
  }
}
