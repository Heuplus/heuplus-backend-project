package com.bola.boilerplate.payload.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePatientRequest {
    private String medications;
    private String medicalHistory;
    private String insuranceProviderName;
    private String insurancePolicyNumber;
}
