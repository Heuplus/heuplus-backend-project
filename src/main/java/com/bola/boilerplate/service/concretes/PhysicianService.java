package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.PhysicianRepository;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/*
 PhysicianManager implementation for handling Patient entity related business logic
*/
public class PhysicianService implements PhysicianManager {
    private final PhysicianRepository repository;
    private final UserManager userManager;

    /*
        Handle creation of a new physician
     */
    @Override
    public CreateResponse create(String email, CreatePhysicianRequest createPhysicianRequest) {
        return null;
    }
}
