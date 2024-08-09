package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.dto.SettingDto;
import com.bola.boilerplate.models.Setting;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreateSettingRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.SettingRepository;
import com.bola.boilerplate.service.abstracts.SettingManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
/*
 SettingManager implementation for handling Setting entity related business logic
*/
public class SettingService implements SettingManager {
  private final SettingRepository repository;
  private final UserManager userManager;

  /*
     Handle creation of a new setting
  */
  @Override
  public CreateResponse create(String email, CreateSettingRequest createSettingRequest) {
    User user = userManager.findUserByEmail(email);
    Setting toCreate =
        Setting.builder()
            .key(createSettingRequest.getKey())
            .value(createSettingRequest.getValue())
            .user(user)
            .build();
    repository.save(toCreate);
    return CreateResponse.builder().message("Setting created successfully").build();
  }

  /*
     Handle getting a setting from database
  */
  @Override
  public SettingDto details(String email, UUID settingId) {
    User user = userManager.findUserByEmail(email);
    return repository.findSettingDtoByIdAndUser(settingId, user.getId()).orElseThrow();
  }

  /*
     Handle getting a list of setting from database
  */
  @Override
  public List<SettingDto> list(String email) {
    User user = userManager.findUserByEmail(email);
    return repository.findSettingsDtoByUser(user.getId());
  }
}
