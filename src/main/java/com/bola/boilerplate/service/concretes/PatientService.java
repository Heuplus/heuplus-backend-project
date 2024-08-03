package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.models.Patient;
import com.bola.boilerplate.repository.PatientRepository;
import com.bola.boilerplate.service.abstracts.PatientManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService implements PatientManager {
    private final PatientRepository repository;


    @Override
    public Patient create(Patient patient) {
        return null;
    }
}
