package com.bola.boilerplate.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bola.boilerplate.config.SpringSecurityUserProvider;
import com.bola.boilerplate.dto.ProcedureDto;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreateProcedureRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.ProcedureRepository;
import com.bola.boilerplate.service.abstracts.PhysicianManager;
import com.bola.boilerplate.service.abstracts.ProcedureManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.NoSuchElementException;
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
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

@AutoConfigureMockMvc
@SpringBootTest(classes = SpringSecurityUserProvider.class)
@TestPropertySource(properties = "spring.main.allow-bean-definition-overriding=true")
@ActiveProfiles("test")
/*
   Testing for ProcedureController
*/
class ProcedureControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean private ProcedureManager procedureManager;
  @MockBean private ProcedureRepository procedureRepository;
  @MockBean private UserManager userManager;
  @MockBean private PhysicianManager physicianManager;
  @Autowired private User user;

  private CreateProcedureRequest createProcedureRequest;

  @BeforeEach
  void setUp() {
    createProcedureRequest =
        CreateProcedureRequest.builder().name("Plastic Surgery").price(500.00).build();
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldPassCreateProcedure() throws Exception {
    Mockito.when(
            procedureManager.create(
                Mockito.any(String.class), Mockito.any(CreateProcedureRequest.class)))
        .thenReturn(new CreateResponse("Procedure created successfully"));
    mockMvc
        .perform(
            post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createProcedureRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(201))
        .andExpect(jsonPath("$.data.message").value("Procedure created successfully"))
        .andExpect(jsonPath("$.message").value("Procedure created successfully"));
  }

  @Test
  @WithMockUser(roles = "PATIENT")
  void shouldFailCreateProcedureForPatient() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createProcedureRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(403))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"));
  }

  @Test
  @WithMockUser(roles = "USER")
  void shouldFailCreateProcedureForUser() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createProcedureRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(403))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"));
  }

  @Test
  void shouldFailCreateProcedureWithoutAuthentication() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createProcedureRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(401))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldFailCreateProcedureMissingName() throws Exception {
    createProcedureRequest.setName(null);
    mockMvc
        .perform(
            post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createProcedureRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(400))
        .andExpect(jsonPath("$.data.name").value("Name field cannot be blank"))
        .andExpect(jsonPath("$.message").value("Name field cannot be blank\n"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldFailCreateProcedureNegativePrice() throws Exception {
    createProcedureRequest.setPrice(-500.00);
    mockMvc
        .perform(
            post("/api/v1/procedures")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createProcedureRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(400))
        .andExpect(jsonPath("$.data.price").value("Price field cannot be negative"))
        .andExpect(jsonPath("$.message").value("Price field cannot be negative\n"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldPassDetailsWithPhysician() throws Exception {
    var randomUUID = UUID.randomUUID();
    Mockito.when(procedureManager.details(Mockito.any(UUID.class)))
        .thenReturn(Mockito.any(ProcedureDto.class));
    mockMvc
        .perform(get("/api/v1/procedures/" + randomUUID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Got procedure successfully"));
  }

  @Test
  @WithMockUser(roles = "PATIENT")
  void shouldPassDetailsWithPatient() throws Exception {
    var randomUUID = UUID.randomUUID();
    Mockito.when(procedureManager.details(Mockito.any(UUID.class)))
        .thenReturn(Mockito.any(ProcedureDto.class));
    mockMvc
        .perform(get("/api/v1/procedures/" + randomUUID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Got procedure successfully"));
  }

  @Test
  @WithMockUser(roles = "USER")
  void shouldFailDetailsWithUserRole() throws Exception {
    var randomUUID = UUID.randomUUID();
    mockMvc
        .perform(get("/api/v1/procedures/" + randomUUID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(403))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"));
  }

  @Test
  void shouldFailDetailsWithoutAuthentication() throws Exception {
    var randomUUID = UUID.randomUUID();
    mockMvc
        .perform(get("/api/v1/procedures/" + randomUUID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(401))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldFailDetailsWithNonExistingEntity() throws Exception {
    var randomUUID = UUID.randomUUID();
    Mockito.when(procedureManager.details(Mockito.any(UUID.class)))
        .thenThrow(NoSuchElementException.class);
    mockMvc
        .perform(get("/api/v1/procedures/" + randomUUID).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.message").value("Not Found"))
        .andExpect(jsonPath("$.data.error").value("Not Found"));
  }
}
