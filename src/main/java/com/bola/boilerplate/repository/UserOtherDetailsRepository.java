package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.UserOtherDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    Data access layer implementation for UserOtherDetails entity
 */
public interface UserOtherDetailsRepository extends JpaRepository<UserOtherDetails, UUID> {}
