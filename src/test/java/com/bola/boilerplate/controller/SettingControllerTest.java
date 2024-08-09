package com.bola.boilerplate.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bola.boilerplate.config.SpringSecurityUserProvider;
import com.bola.boilerplate.dto.SettingDto;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreateSettingRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.SettingRepository;
import com.bola.boilerplate.service.abstracts.SettingManager;
import com.bola.boilerplate.service.abstracts.UserManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
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
class SettingControllerTest {
  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;
  @MockBean SettingRepository settingRepository;
  @MockBean UserManager userManager;
  @MockBean SettingManager settingManager;
  @Autowired private User user;

  private CreateSettingRequest createSettingRequest;
  private SettingDto settingDto;
  private UUID randomUuid;

  private List<SettingDto> settingDtoList;

  @BeforeEach
  void setUp() {
    createSettingRequest =
        CreateSettingRequest.builder().key("appointment_buffer_time").value("60").build();
    randomUuid = UUID.randomUUID();
    settingDto =
        SettingDto.builder()
            .settingId(randomUuid)
            .key("appointment_buffer_time")
            .value("60")
            .build();
    settingDtoList = new ArrayList<>();
    settingDtoList.add(settingDto);
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldPassCreateSettingWithPhysician() throws Exception {
    Mockito.when(
            settingManager.create(
                Mockito.any(String.class), Mockito.any(CreateSettingRequest.class)))
        .thenReturn(new CreateResponse("Setting created successfully"));
    mockMvc
        .perform(
            post("/api/v1/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createSettingRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(201))
        .andExpect(jsonPath("$.data.message").value("Setting created successfully"))
        .andExpect(jsonPath("$.message").value("Setting created successfully"));
  }

  @Test
  @WithMockUser(roles = "PATIENT")
  void shouldPassCreateSettingWithPatient() throws Exception {
    Mockito.when(
            settingManager.create(
                Mockito.any(String.class), Mockito.any(CreateSettingRequest.class)))
        .thenReturn(new CreateResponse("Setting created successfully"));
    mockMvc
        .perform(
            post("/api/v1/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createSettingRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(201))
        .andExpect(jsonPath("$.data.message").value("Setting created successfully"))
        .andExpect(jsonPath("$.message").value("Setting created successfully"));
  }

  @Test
  @WithMockUser(roles = "USER")
  void shouldFailCreateSettingWithUserRole() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createSettingRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(403))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"));
  }

  @Test
  void shouldFailCreateSettingWithoutAuthentication() throws Exception {
    mockMvc
        .perform(
            post("/api/v1/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createSettingRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(401))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldFailCreateSettingWithBlankKey() throws Exception {
    createSettingRequest.setKey(null);
    mockMvc
        .perform(
            post("/api/v1/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createSettingRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(400))
        .andExpect(jsonPath("$.data.key").value("Key field cannot be blank"))
        .andExpect(jsonPath("$.message").value("Key field cannot be blank\n"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldFailCreateSettingWithBlankValue() throws Exception {
    createSettingRequest.setValue(null);
    mockMvc
        .perform(
            post("/api/v1/settings")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(createSettingRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(400))
        .andExpect(jsonPath("$.data.value").value("Value field cannot be blank"))
        .andExpect(jsonPath("$.message").value("Value field cannot be blank\n"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldPassDetailsWithPhysician() throws Exception {
    Mockito.when(settingManager.details(Mockito.any(String.class), Mockito.any(UUID.class)))
        .thenReturn(settingDto);
    mockMvc
        .perform(get("/api/v1/settings/" + randomUuid).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Got setting successfully"))
        .andExpect(jsonPath("$.data.settingId").value(randomUuid.toString()))
        .andExpect(jsonPath("$.data.key").value(settingDto.getKey()))
        .andExpect(jsonPath("$.data.value").value(settingDto.getValue()));
  }

  @Test
  @WithMockUser(roles = "PATIENT")
  void shouldPassDetailsWithPatient() throws Exception {
    Mockito.when(settingManager.details(Mockito.any(String.class), Mockito.any(UUID.class)))
        .thenReturn(settingDto);
    mockMvc
        .perform(get("/api/v1/settings/" + randomUuid).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Got setting successfully"))
        .andExpect(jsonPath("$.data.settingId").value(randomUuid.toString()))
        .andExpect(jsonPath("$.data.key").value(settingDto.getKey()))
        .andExpect(jsonPath("$.data.value").value(settingDto.getValue()));
  }

  @Test
  @WithMockUser(roles = "USER")
  void shouldFailDetailsWithUserRole() throws Exception {
    mockMvc
        .perform(get("/api/v1/settings/" + randomUuid).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(403))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"));
  }

  @Test
  void shouldFailDetailsWithoutAuthentication() throws Exception {
    mockMvc
        .perform(get("/api/v1/settings/" + randomUuid).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(401))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldFailDetailsWithNotFound() throws Exception {
    Mockito.when(settingManager.details(Mockito.any(String.class), Mockito.any(UUID.class)))
        .thenThrow(new NoSuchElementException());
    mockMvc
        .perform(get("/api/v1/settings/" + randomUuid).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(404))
        .andExpect(jsonPath("$.message").value("Not Found"))
        .andExpect(jsonPath("$.data.error").value("Not Found"));
  }

  @Test
  @WithMockUser(roles = "PHYSICIAN")
  void shouldPassListWithPhysician() throws Exception {
    Mockito.when(settingManager.list(Mockito.any(String.class))).thenReturn(settingDtoList);
    mockMvc
        .perform(get("/api/v1/settings").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Got settings successfully"))
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  @WithMockUser(roles = "PATIENT")
  void shouldPassListWithPatient() throws Exception {
    Mockito.when(settingManager.list(Mockito.any(String.class))).thenReturn(settingDtoList);
    mockMvc
        .perform(get("/api/v1/settings").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(200))
        .andExpect(jsonPath("$.message").value("Got settings successfully"))
        .andExpect(jsonPath("$.data").isArray());
  }

  @Test
  @WithMockUser(roles = "USER")
  void shouldFailListWithUserRole() throws Exception {
    mockMvc
        .perform(get("/api/v1/settings").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(403))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"));
  }

  @Test
  void shouldFailListWithoutAuthentication() throws Exception {
    mockMvc
        .perform(get("/api/v1/settings").contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.statusCode").value(401))
        .andExpect(jsonPath("$.message").value("Not authorized for the action"))
        .andExpect(jsonPath("$.data.error").value("Not authorized for the action"));
  }
}
