package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.payload.request.CreateSettingRequest;
import com.bola.boilerplate.payload.response.CreateResponse;

/*
 Implementation blueprint for SettingService
*/
public interface SettingManager {
  /*
   Implementation blueprint for creating a new Setting in database
  */
  CreateResponse create(String email, CreateSettingRequest createSettingRequest);
}
