package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.models.UserOtherDetails;
import com.bola.boilerplate.repository.UserOtherDetailsRepository;
import com.bola.boilerplate.service.abstracts.UserOtherDetailsManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
/*
 UserOtherDetailsManager implementation for handling UserOtherDetails entity related business logic
*/
public class UserOtherDetailsService implements UserOtherDetailsManager {
  private final UserOtherDetailsRepository repository;

  @Override
  public UserOtherDetails create(UserOtherDetails userOtherDetails) {
    return repository.save(userOtherDetails);
  }
}
