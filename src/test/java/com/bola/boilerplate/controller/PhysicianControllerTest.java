package com.bola.boilerplate.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bola.boilerplate.config.SpringSecurityUserProvider;
import com.bola.boilerplate.dto.PhysicianDto;
import com.bola.boilerplate.dto.PhysicianSelfDto;
import com.bola.boilerplate.exception.exceptions.NotAllowedForTheAction;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreatePhysicianRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.PhysicianRepository;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringSecurityUserProvider.class)
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
/*
   Testing for PhysicianController
*/
public class PhysicianControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private PhysicianManager physicianManager;

  @MockBean private PhysicianRepository physicianRepository;

  @MockBean private UserManager userManager;

  @Autowired private User user;

  private CreatePhysicianRequest createPhysicianRequest;

  @BeforeEach
  void setUp() {
    createPhysicianRequest =
        CreatePhysicianRequest.builder()
            .description("World known Aesthetics Surgeon with more than 7 years of experience")
            .educationRecord("{\"Cukurova University\": \"Med School\"}")
            .previousExperience(
                "{\"Acı Badem Hospital\": \"4 years\", \"Medipol Hospital\" : \"3 years\"}")
            .specialization("Aesthetics")
            .qualifications("Nose Aesthetics, Plastic Surgery")
            .build();
  }

  @Test
  @WithUserDetails
  void shouldPassCreatePatient() throws Exception {
    Mockito.when(
            physicianManager.create(
                Mockito.any(String.class), Mockito.any(CreatePhysicianRequest.class)))
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
    Mockito.when(
            physicianManager.create(
                Mockito.any(String.class), Mockito.any(CreatePhysicianRequest.class)))
        .thenReturn(new CreateResponse("Patient created successfully"));
    mockMvc
        .perform(
            post("/api/v1/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createPhysicianRequest)))
        .andExpect(jsonPath("$.statusCode").value(401));
  }

  @Test
  @WithMockUser(roles = "PATIENT")
  void shouldPassPatientDetailsForPatient() throws Exception {
    var randomId = UUID.randomUUID();
    Mockito.when(physicianManager.getPhysicianDetails(Mockito.any(UUID.class)))
        .thenReturn(Mockito.any(PhysicianDto.class));
    mockMvc
        .perform(get("/api/v1/physicians/" + randomId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Got the physician's details"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldPassPatientDetailsForPhysician() throws Exception {
    var randomId = UUID.randomUUID();
    Mockito.when(physicianManager.getPhysicianDetails(Mockito.any(UUID.class)))
        .thenReturn(Mockito.any(PhysicianDto.class));
    mockMvc
        .perform(get("/api/v1/physicians/" + randomId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Got the physician's details"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldPassPatientDetailsForPhysicianSelf() throws Exception {
    Mockito.when(physicianManager.getPhysicianDetails(Mockito.any(String.class)))
        .thenReturn(Mockito.any(PhysicianSelfDto.class));
    mockMvc
        .perform(get("/api/v1/physicians/").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Got the physician's details"));
  }

  @Test
  @WithMockUser(roles = "PATIENT")
  void shouldFailPatientDetailsForPhysicianSelfWithPatient() throws Exception {
    Mockito.when(physicianManager.getPhysicianDetails(Mockito.any(String.class)))
        .thenThrow(NotAllowedForTheAction.class);
    mockMvc
        .perform(get("/api/v1/physicians/").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(403))
        .andExpect(jsonPath("$.message").value("Not allowed for the action"))
        .andExpect(jsonPath("$.data.error").value("Not allowed for the action"));
  }

  @Test
  @WithMockUser(roles = "USER")
  void shouldFailPatientDetailsWithUserRole() throws Exception {
    var randomId = UUID.randomUUID();
    mockMvc
        .perform(get("/api/v1/physicians/" + randomId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(403))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"));
  }

  @Test
  void shouldFailPatientDetailsWithoutAuthentication() throws Exception {
    var randomId = UUID.randomUUID();
    mockMvc
        .perform(get("/api/v1/physicians/" + randomId).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(401))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"));
  }
}
