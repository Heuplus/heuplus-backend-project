package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.models.Role;
import com.bola.boilerplate.models.User;

public interface UserManager {
  User setUserRole(User user, Role role);

  User findUserByEmail(String email);
}
