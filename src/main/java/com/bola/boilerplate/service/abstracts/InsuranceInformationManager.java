package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.models.InsuranceInformation;

/*
 Implementation blueprint for InsuranceInformationService
*/
public interface InsuranceInformationManager {
  /*
   Implementation blueprint for creating a new InsuranceInformation instance in database
  */
  InsuranceInformation create(String providerName, String policyNumber);
}
