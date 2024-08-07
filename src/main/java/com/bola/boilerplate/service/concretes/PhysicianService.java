package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.dto.PhysicianDto;
import com.bola.boilerplate.dto.PhysicianSelfDto;
import com.bola.boilerplate.exception.exceptions.NotAllowedForTheActionException;
import com.bola.boilerplate.exception.exceptions.RoleChangeNotPossibleException;
import com.bola.boilerplate.models.Physician;
import com.bola.boilerplate.models.Role;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.PhysicianRepository;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
/*
 PhysicianManager implementation for handling Patient entity related business logic
*/
public class PhysicianService implements PhysicianManager {
  private final PhysicianRepository repository;
  private final UserManager userManager;

  /*
     Handle creation of a new physician
  */
  @Override
  public CreateResponse create(String email, CreatePhysicianRequest createPhysicianRequest) {
    User user = userManager.findUserByEmail(email);
    if (user.getRole() != Role.ROLE_USER) {
      throw new RoleChangeNotPossibleException("Role change is not allowed");
    }

    User updateUser = userManager.setUserRole(user, Role.ROLE_PHYSICIAN);
    Physician physician =
        Physician.builder()
            .user(updateUser)
            .description(createPhysicianRequest.getDescription())
            .qualifications(createPhysicianRequest.getQualifications())
            .educationRecord(createPhysicianRequest.getEducationRecord())
            .previousExperience(createPhysicianRequest.getPreviousExperience())
            .specialization(createPhysicianRequest.getSpecialization())
            .build();
    repository.save(physician);
    return new CreateResponse("Physician created successfully");
  }

  /*
     Gets a Physician's details for other physicians and patients
  */
  @Override
  public PhysicianDto getPhysicianDetails(UUID id) {
    var physician = repository.findById(id).orElseThrow();
    return PhysicianDto.builder()
        .physicianId(id)
        .profilePhotoUrl(physician.getUser().getUserOtherDetails().getProfilePhotoUrl())
        .description(physician.getDescription())
        .specialization(physician.getSpecialization())
        .educationRecord(physician.getEducationRecord())
        .previousExperience(physician.getPreviousExperience())
        .qualifications(physician.getQualifications())
        .firstName(physician.getUser().getUserOtherDetails().getLastName())
        .lastName(physician.getUser().getUserOtherDetails().getLastName())
        .build();
  }

  /*
     Gets a Physician's details for physician's self
  */
  @Override
  public PhysicianSelfDto getPhysicianDetails(String email) {
    var user = userManager.findUserByEmail(email);
    System.out.println("Got into self details with email" + user.getUsername());
    if (user.getRole() != Role.ROLE_PHYSICIAN) {
      System.out.println("Role is not physician");
      throw new NotAllowedForTheActionException();
    }
    var physician = repository.findByUser(user).orElseThrow();
    return PhysicianSelfDto.builder()
        .physicianId(physician.getId())
        .profilePhotoUrl(physician.getUser().getUserOtherDetails().getProfilePhotoUrl())
        .description(physician.getDescription())
        .specialization(physician.getSpecialization())
        .educationRecord(physician.getEducationRecord())
        .previousExperience(physician.getPreviousExperience())
        .qualifications(physician.getQualifications())
        .firstName(physician.getUser().getUserOtherDetails().getLastName())
        .lastName(physician.getUser().getUserOtherDetails().getLastName())
        .email(physician.getUser().getEmail())
        .phoneNumber(physician.getUser().getUserOtherDetails().getPhoneNumber())
        .dateOfBirth(physician.getUser().getUserOtherDetails().getDateOfBirth())
        .gender(physician.getUser().getUserOtherDetails().getGender())
        .build();
  }

  /*
     Gets a list of physicians
  */
  @Override
  public Page<PhysicianDto> listPhysicians(Pageable pageable) {
    return repository
        .findAll(pageable)
        .map(
            entity ->
                PhysicianDto.builder()
                    .physicianId(entity.getId())
                    .specialization(entity.getSpecialization())
                    .profilePhotoUrl(entity.getUser().getUserOtherDetails().getProfilePhotoUrl())
                    .educationRecord(entity.getEducationRecord())
                    .previousExperience(entity.getPreviousExperience())
                    .qualifications(entity.getQualifications())
                    .description(entity.getDescription())
                    .firstName(entity.getUser().getUserOtherDetails().getFirstName())
                    .lastName(entity.getUser().getUserOtherDetails().getLastName())
                    .build());
  }

  /*
     Gets the physician by User
  */
  @Override
  public Physician getPhysicianByUser(User user) {
    return repository.findByUser(user).orElseThrow();
  }
}
