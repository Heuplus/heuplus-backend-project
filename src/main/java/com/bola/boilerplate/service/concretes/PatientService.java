package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.dto.PatientDto;
import com.bola.boilerplate.exception.exceptions.RoleChangeNotPossibleException;
import com.bola.boilerplate.models.InsuranceInformation;
import com.bola.boilerplate.models.Patient;
import com.bola.boilerplate.models.Role;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreatePatientRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.PatientRepository;
import com.bola.boilerplate.service.abstracts.InsuranceInformationManager;
import com.bola.boilerplate.service.abstracts.PatientManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PatientService implements PatientManager {
  private final PatientRepository repository;
  private final InsuranceInformationManager insuranceInformationManager;

  private final UserManager userManager;

  @Override
  public CreateResponse create(User user, CreatePatientRequest createPatientRequest) {
    // check if the user already has role
    if (user.getRole() != Role.ROLE_USER) {
      throw new RoleChangeNotPossibleException("Role change is not possible");
    }

    User updatedUser = userManager.setUserRole(user, Role.ROLE_PATIENT);

    InsuranceInformation insuranceInformation =
        insuranceInformationManager.create(
            createPatientRequest.getInsuranceProviderName(),
            createPatientRequest.getInsurancePolicyNumber());

    Patient toCreate =
        Patient.builder()
            .medications(createPatientRequest.getMedications())
            .medicalHistory(createPatientRequest.getMedicalHistory())
            .insuranceInformation(insuranceInformation)
            .user(updatedUser)
            .build();

    repository.save(toCreate);

    return new CreateResponse("Patient created successfully");
  }

  @Override
  public PatientDto details(String email) {
    Patient patient = repository.findByUserEmail(email);
    return PatientDto.builder()
        .userId(patient.getUser().getId())
        .patientId(patient.getId())
        .email(patient.getUser().getEmail())
        .createdAt(patient.getCreatedAt())
        .updatedAt(patient.getUpdatedAt())
        .dateOfBirth(patient.getUser().getUserOtherDetails().getDateOfBirth())
        .firstName(patient.getUser().getUserOtherDetails().getFirstName())
        .lastName(patient.getUser().getUserOtherDetails().getLastName())
        .phoneNumber(patient.getUser().getUserOtherDetails().getPhoneNumber())
        .gender(patient.getUser().getUserOtherDetails().getGender())
        .profilePhotoUrl(patient.getUser().getUserOtherDetails().getProfilePhotoUrl())
        .medications(patient.getMedications())
        .medicalHistory(patient.getMedicalHistory())
        .insuranceProviderName(patient.getInsuranceInformation().getProviderName())
        .insurancePolicyNumber(patient.getInsuranceInformation().getPolicyNumber())
        .build();
  }
}
