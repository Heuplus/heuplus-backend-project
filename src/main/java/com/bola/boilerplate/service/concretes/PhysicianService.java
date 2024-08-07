package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.dto.PhysicianDto;
import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.repository.PhysicianRepository;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhysicianService implements PhysicianManager {
    private final PhysicianRepository repository;

    @Override
    public PhysicianDto create(String email, CreatePhysicianRequest createPhysicianRequest) {
        return null;
    }
}
