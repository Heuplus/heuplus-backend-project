package com.bola.boilerplate.security;

import com.bola.boilerplate.payload.response.ResultWithData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/** Custom AuthenticationEntryPoint implementation for better error handling */
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /**
   * This method is handles the error in a better way
   *
   * @param request - the request that was made
   * @param response - the response that will be sent
   * @param authException - the exception that was thrown
   * @throws IOException - if an input or output exception occurs
   */
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_OK);

    Map<String, String> error = Map.of("error", "Not authorized for the action");
    ResultWithData<Object> data =
        ResultWithData.builder()
            .data(error)
            .statusCode(401)
            .message("Not authorized for the action")
            .build();

    new ObjectMapper().writeValue(response.getOutputStream(), data);
  }
}
