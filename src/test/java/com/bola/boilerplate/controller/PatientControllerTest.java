package com.bola.boilerplate.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.bola.boilerplate.config.SpringSecurityUserProvider;
import com.bola.boilerplate.exception.exceptions.RoleChangeNotPossibleException;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreatePatientRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.PatientRepository;
import com.bola.boilerplate.security.JwtService;
import com.bola.boilerplate.service.abstracts.InsuranceInformationManager;
import com.bola.boilerplate.service.abstracts.PatientManager;
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
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(
        classes = SpringSecurityUserProvider.class
)
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
public class PatientControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private PatientManager manager;
    @MockBean
    private PatientRepository repository;
    @MockBean
    private InsuranceInformationManager insuranceInformationManager;
    @MockBean
    private UserManager userManager;
    @MockBean
    private JwtService jwtService;
    @Autowired
    private User user;
    private CreatePatientRequest createPatientRequest;

    @BeforeEach
    void setUp() {
        createPatientRequest = CreatePatientRequest.builder()
                .insurancePolicyNumber("asd")
                .insuranceProviderName("123")
                .medicalHistory("{\\\"tension\\\":\\\"Coraspin\\\"}")
                .medications("{\\\"sicknesses\\\":[\\\"Hyper Tension\\\", \\\"Diabetes\\\"]}")
                .build();
    }

    @Test
    @WithUserDetails
    void shouldPassCreatePatient() throws Exception {
        Mockito.when(manager.create(Mockito.any(User.class), Mockito.any(CreatePatientRequest.class))).thenReturn(new CreateResponse("Patient created successfully"));
        mockMvc.perform(post("/api/v1/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPatientRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Patient created successfully"));
    }

    @Test
    void shouldFailWithoutAuthentication() throws Exception {
        Mockito.when(manager.create(Mockito.any(User.class), Mockito.any(CreatePatientRequest.class))).thenReturn(new CreateResponse("Patient created successfully"));
        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createPatientRequest)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails
    void shouldFailWithRoleChangeNotPossibleException() throws Exception {
        Mockito.when(manager.create(Mockito.any(User.class), Mockito.any(CreatePatientRequest.class))).thenThrow(new RoleChangeNotPossibleException("Role change is not possible"));
        mockMvc.perform(post("/api/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(createPatientRequest)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.message").value("Role change is not possible"));
    }
}
