package com.bola.boilerplate.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.bola.boilerplate.controllers.AuthenticationController;
import com.bola.boilerplate.models.Gender;
import com.bola.boilerplate.payload.request.AuthenticationRequest;
import com.bola.boilerplate.payload.request.RegisterRequest;
import com.bola.boilerplate.payload.response.AuthenticationResponse;
import com.bola.boilerplate.repository.UserRepository;
import com.bola.boilerplate.security.JwtService;
import com.bola.boilerplate.service.abstracts.AuthenticationManager;
import com.bola.boilerplate.service.abstracts.UserOtherDetailsManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

/* Unit tests for {@link AuthenticationController} */
@WebMvcTest(controllers = AuthenticationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
class AuthenticationControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private ObjectMapper objectMapper;

  @MockBean private AuthenticationManager authenticationManager;
  @MockBean private UserRepository repository;
  @MockBean private PasswordEncoder passwordEncoder;
  @MockBean private JwtService jwtService;
  @MockBean private UserOtherDetailsManager userOtherDetailsService;

  @MockBean
  private org.springframework.security.authentication.AuthenticationManager authenticationManager2;

  private RegisterRequest registerRequest;

  private AuthenticationRequest authenticationRequest;

  @BeforeEach
  void setup() {
    registerRequest =
        RegisterRequest.builder()
            .firstName("John")
            .lastName("Doe")
            .email("me@here.com")
            .password("111111aA*")
            .gender(Gender.MALE)
            .dateOfBirth(
                Date.from(
                    LocalDate.of(1997, 2, 19).atStartOfDay(ZoneId.systemDefault()).toInstant()))
            .build();
    authenticationRequest =
        AuthenticationRequest.builder().email("me@here.com").password("111111aA*").build();
  }

  @Test
  void registerShouldPass() throws Exception {
    Mockito.when(authenticationManager.register(Mockito.any(RegisterRequest.class)))
        .thenReturn(new AuthenticationResponse("some-token"));
    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registerRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("some-token"));
  }

  @Test
  void registerShouldFailWithMissingFirstName() throws Exception {
    registerRequest.setFirstName(null);

    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.firstName").value("First Name field cannot be blank"));
  }

  @Test
  void registerShouldFailWithMissingLastName() throws Exception {
    registerRequest.setLastName(null);
    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.lastName").value("Last Name field cannot be blank"));
  }

  @Test
  void registerShouldFailWithMissingEmail() throws Exception {
    registerRequest.setEmail(null);
    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Email field cannot be blank"));
  }

  @Test
  void registerShouldFailWithWrongEmailFormat() throws Exception {
    registerRequest.setEmail("me@there");
    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Wrong email format"));
  }

  @Test
  void registerShouldFailWithMissingPassword() throws Exception {
    registerRequest.setPassword(null);
    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.password").value("Password field cannot be blank"));
  }

  @Test
  void registerShouldFailWithWrongPasswordFormat() throws Exception {
    registerRequest.setPassword("11111111");
    mockMvc
        .perform(
            post("/api/v1/auth/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(registerRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.password")
                .value(
                    "Password must consists of 1 uppercase letter, 1 lowercase letter, 1 numeric"
                        + " character and at least 8 characters long"));
  }

  @Test
  void authenticationShouldSuccess() throws Exception {
    Mockito.when(authenticationManager.authenticate(Mockito.any(AuthenticationRequest.class)))
        .thenReturn(new AuthenticationResponse("some-token"));
    mockMvc
        .perform(
            post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(authenticationRequest)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.token").value("some-token"));
  }

  @Test
  void authenticateShouldFailWithNonExistingAccount() throws Exception {
    AuthenticationRequest newAuthenticationRequest =
        AuthenticationRequest.builder().email("he@here.com").password("111111aA*").build();
    Mockito.when(authenticationManager.authenticate(Mockito.any(AuthenticationRequest.class)))
        .thenThrow(new AuthenticationException("asd") {});
    mockMvc
        .perform(
            post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(newAuthenticationRequest)))
        .andExpect(status().isForbidden());
  }

  @Test
  void authenticateShouldFailWithMissingEmail() throws Exception {
    authenticationRequest.setEmail(null);
    mockMvc
        .perform(
            post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(authenticationRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Email field cannot be blank"));
  }

  @Test
  void authenticateShouldFailWithWrongEmailFormat() throws Exception {
    authenticationRequest.setEmail("me@there");
    mockMvc
        .perform(
            post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(authenticationRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.email").value("Wrong email format"));
  }

  @Test
  void authenticateShouldFailWithMissingPassword() throws Exception {
    authenticationRequest.setPassword(null);
    mockMvc
        .perform(
            post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(authenticationRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.password").value("Password field cannot be blank"));
  }

  @Test
  void authenticateShouldFailWithWrongPasswordFormat() throws Exception {
    authenticationRequest.setPassword("11111111");
    mockMvc
        .perform(
            post("/api/v1/auth/authenticate")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(authenticationRequest)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath("$.password")
                .value(
                    "Password must consists of 1 uppercase letter, 1 lowercase letter, 1 numeric"
                        + " character and at least 8 characters long"));
  }
}
