package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.models.InsuranceInformation;

public interface InsuranceInformationManager {
  InsuranceInformation create(String providerName, String policyNumber);
}
