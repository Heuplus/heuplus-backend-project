package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.UserOtherDetails;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserOtherDetailsRepository extends JpaRepository<UserOtherDetails, UUID> {}
