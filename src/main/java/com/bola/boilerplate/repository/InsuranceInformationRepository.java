package com.bola.boilerplate.repository;

import com.bola.boilerplate.models.InsuranceInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InsuranceInformationRepository extends JpaRepository<InsuranceInformation, UUID> {
}
