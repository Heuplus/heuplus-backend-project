package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.models.InsuranceInformation;
import com.bola.boilerplate.repository.InsuranceInformationRepository;
import com.bola.boilerplate.service.abstracts.InsuranceInformationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InsuranceInformationService implements InsuranceInformationManager {
  private final InsuranceInformationRepository repository;

  @Override
  public InsuranceInformation create(String providerName, String policyNumber) {
    InsuranceInformation toCreate =
        InsuranceInformation.builder()
            .providerName(providerName)
            .policyNumber(policyNumber)
            .build();

    return repository.save(toCreate);
  }
}
