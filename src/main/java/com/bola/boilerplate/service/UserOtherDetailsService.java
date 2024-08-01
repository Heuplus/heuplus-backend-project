package com.bola.boilerplate.service;

import com.bola.boilerplate.models.User;
import com.bola.boilerplate.models.UserOtherDetails;
import com.bola.boilerplate.repository.UserOtherDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserOtherDetailsService {
    private final UserOtherDetailsRepository repository;

    public UserOtherDetails create(String firstName, String lastName, User user) {
        UserOtherDetails toCreate = UserOtherDetails.builder()
                .firstName(firstName)
                .lastName(lastName)
                .user(user)
                .build();
        return repository.save(toCreate);
    }
}
