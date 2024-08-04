package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.models.Role;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.repository.UserRepository;
import com.bola.boilerplate.service.abstracts.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserManager {
  private final UserRepository repository;

  @Override
  public User setUserRole(User user, Role role) {
    user.setRole(role);
    return repository.save(user);
  }
}
