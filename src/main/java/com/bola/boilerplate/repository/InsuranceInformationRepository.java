package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.InsuranceInformation;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

/*
    Data access layer implementation for InsuranceInformation entity
 */
public interface InsuranceInformationRepository extends JpaRepository<InsuranceInformation, UUID> {}
