package com.bola.boilerplate.controllers;

import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.ResultWithData;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/physicians")
@RequiredArgsConstructor
public class PhysicianController {
    private final PhysicianManager service;

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
    public ResponseEntity<ResultWithData<Object>> create(@AuthenticationPrincipal UserDetails userDetails, @RequestBody CreatePhysicianRequest createPhysicianRequest) {
        String email = userDetails.getUsername();
        var result = ResultWithData.builder()
                .message("Converted USER account to PHYSICIAN account successfully")
                .data(service.create(email, createPhysicianRequest))
                .statusCode(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(result);
    }
}
