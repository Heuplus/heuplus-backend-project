package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.models.Role;
import com.bola.boilerplate.models.User;

public interface UserManager {
  /*
    Implementation blueprint for updating a user's role
   */
  User setUserRole(User user, Role role);

  /*
    Implementation blueprint for getting a user's details by email
   */
  User findUserByEmail(String email);
}
