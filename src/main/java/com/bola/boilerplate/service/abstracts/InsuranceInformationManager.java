package com.bola.boilerplate.service.abstracts;

import com.bola.boilerplate.models.InsuranceInformation;
import com.bola.boilerplate.models.Patient;

public interface InsuranceInformationManager {
    InsuranceInformation create(String providerName, String policyNumber);
}
