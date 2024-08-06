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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/*
   Custom AuthenticationEntryPoint implementation for better error handling
*/
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
  /*
     Handles the error in a better way
  */
  @Override
  public void commence(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException authException)
      throws IOException, ServletException {
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_OK);
    OutputStream responseStream = response.getOutputStream();
    ObjectMapper objectMapper = new ObjectMapper();
    HashMap<String, String> error = new HashMap<>();
    String key = "error";
    String message = "Not authorized for the action";
    error.put(key, message);
    var data = ResultWithData.builder().data(error).statusCode(401).message(message).build();
    objectMapper.writeValue(responseStream, data);
    responseStream.flush();
  }
}
