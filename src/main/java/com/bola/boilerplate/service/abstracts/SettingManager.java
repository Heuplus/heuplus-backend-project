package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.dto.SettingDto;
import com.bola.boilerplate.payload.request.CreateSettingRequest;
import com.bola.boilerplate.payload.response.CreateResponse;

import java.util.List;
import java.util.UUID;

/*
 Implementation blueprint for SettingService
*/
public interface SettingManager {
  /*
   Implementation blueprint for creating a new Setting in database
  */
  CreateResponse create(String email, CreateSettingRequest createSettingRequest);

  /*
    Implementation blueprint for getting a Setting from database
   */
  SettingDto details(String email, UUID settingId);

  /*
    Implementation blueprint for getting a list of settings of a user from database
   */
  List<SettingDto> list(String email);
}
