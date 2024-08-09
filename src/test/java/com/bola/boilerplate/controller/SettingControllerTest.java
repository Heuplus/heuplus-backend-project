package com.bola.boilerplate.controller;

import com.bola.boilerplate.config.SpringSecurityUserProvider;
import com.bola.boilerplate.models.User;
import com.bola.boilerplate.payload.request.CreateSettingRequest;
import com.bola.boilerplate.payload.response.CreateResponse;
import com.bola.boilerplate.repository.SettingRepository;
import com.bola.boilerplate.service.abstracts.SettingManager;
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
import org.springframework.security.test.context.support.WithMockUser;
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
   Testing for ProcedureController
*/
public class SettingControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @MockBean
    SettingRepository settingRepository;
    @MockBean
    UserManager userManager;
    @MockBean
    SettingManager settingManager;
    @Autowired
    private User user;

    private CreateSettingRequest createSettingRequest;

    @BeforeEach
    void setUp() {
        createSettingRequest = CreateSettingRequest.builder()
                .key("appointment_buffer_time")
                .value("60")
                .build();
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
}
