package com.bola.boilerplate.controller;

import com.bola.boilerplate.config.SpringSecurityUserProvider;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreatePatientRequest;
import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.PhysicianRepository;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringSecurityUserProvider.class)
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
/*
    Testing for PhysicianController
 */
public class PhysicianControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PhysicianManager physicianManager;

    @MockBean
    private PhysicianRepository physicianRepository;

    @MockBean
    private UserManager userManager;

    @Autowired
    private User user;

    private CreatePhysicianRequest createPhysicianRequest;

    @BeforeEach
    void setUp() {
        createPhysicianRequest = CreatePhysicianRequest.builder()
                .description("World known Aesthetics Surgeon with more than 7 years of experience")
                .educationRecord("{\"Cukurova University\": \"Med School\"}")
                .previousExperience("{\"Acı Badem Hospital\": \"4 years\", \"Medipol Hospital\" : \"3 years\"}")
                .specialization("Aesthetics")
                .qualifications("Nose Aesthetics, Plastic Surgery")
                .build();
    }

    @Test
    @WithUserDetails
    void shouldPassCreatePatient() throws Exception {
        Mockito.when(physicianManager.create(Mockito.any(String.class), Mockito.any(CreatePhysicianRequest.class)))
                .thenReturn(new CreateResponse("Patient created successfully"));
        mockMvc
                .perform(
                        post("/api/v1/physicians")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(createPhysicianRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode").value(201))
                .andExpect(jsonPath("$.data.message").value("Patient created successfully"));
    }

    @Test
    void shouldFailWithoutAuthentication() throws Exception {
        Mockito.when(physicianManager.create(Mockito.any(String.class), Mockito.any(CreatePhysicianRequest.class)))
                .thenReturn(new CreateResponse("Patient created successfully"));
        mockMvc
                .perform(
                        post("/api/v1/patients")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(createPhysicianRequest)))
                .andExpect(jsonPath("$.statusCode").value(401));
    }
}
