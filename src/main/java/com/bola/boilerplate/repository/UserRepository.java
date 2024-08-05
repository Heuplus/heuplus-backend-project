package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.User;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/*
   Data access layer implementation for User entity
*/
public interface UserRepository extends JpaRepository<User, UUID> {

  /*
   Implementation blueprint for getting a User from database by email
  */
  Optional<User> findByEmail(String email);
}
