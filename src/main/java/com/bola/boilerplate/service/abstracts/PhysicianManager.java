package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.dto.PhysicianDto;
import com.bola.boilerplate.payload.request.CreatePhysicianRequest;

public interface PhysicianManager {
    PhysicianDto create(String email, CreatePhysicianRequest createPhysicianRequest);
}
