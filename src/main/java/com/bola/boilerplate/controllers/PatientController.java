package com.bola.boilerplate.controllers;

import com.bola.boilerplate.service.abstracts.PatientManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {
    private final PatientManager service;

    @PostMapping("/")
    ResponseEntity<Object> create() {
        return null;
    }
}
