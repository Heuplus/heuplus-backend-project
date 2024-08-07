package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.CreateResponse;

public interface PhysicianManager {
    CreateResponse create(String email, CreatePhysicianRequest createPhysicianRequest);
}
