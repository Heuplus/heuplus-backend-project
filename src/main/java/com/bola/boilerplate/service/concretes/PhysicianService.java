package com.bola.boilerplate.service.concretes;

import com.bola.boilerplate.exception.exceptions.RoleChangeNotPossibleException;
import com.bola.boilerplate.models.Physician;
import com.bola.boilerplate.models.Role;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.PhysicianRepository;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import lombok.RequiredArgsConstructor;
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
        if(user.getRole() != Role.ROLE_USER) {
            throw new RoleChangeNotPossibleException();
        }

        User updateUser = userManager.setUserRole(user, Role.ROLE_PHYSICIAN);
        Physician physician = Physician.builder()
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
}
