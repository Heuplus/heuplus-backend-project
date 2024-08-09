package com.bola.boilerplate.security;

import com.bola.boilerplate.payload.response.ResultWithData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

/**
 * Custom AuthenticationEntryPoint implementation for better error handling
*/
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
  /**
   * Handles the error in a better way
  */
  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
          throws IOException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_OK);

    HashMap<String, String> error = new HashMap<>();
    error.put("error", "Not authorized for the action");

    ResultWithData<Object> data = ResultWithData.builder()
            .data(error)
            .statusCode(403)
            .message("Not authorized for the action")
            .build();

    try (OutputStream responseStream = response.getOutputStream()) {
      new ObjectMapper().writeValue(responseStream, data);
      responseStream.flush();
    }
  }
}
