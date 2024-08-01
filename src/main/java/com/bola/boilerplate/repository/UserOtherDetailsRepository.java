package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.UserOtherDetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserOtherDetailsRepository extends JpaRepository<UserOtherDetails, UUID> {
}
