package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.dto.PhysicianDto;
import com.bola.boilerplate.dto.PhysicianSelfDto;
import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.CreateResponse;

import java.util.UUID;

public interface PhysicianManager {
    CreateResponse create(String email, CreatePhysicianRequest createPhysicianRequest);
    PhysicianDto getPhysicianDetails(UUID id);
    PhysicianSelfDto getPhysicianDetails(String email);
}
