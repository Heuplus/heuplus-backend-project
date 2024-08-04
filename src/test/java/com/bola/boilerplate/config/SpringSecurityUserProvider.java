package com.bola.boilerplate.config;

import com.bola.boilerplate.models.Role;
import com.bola.boilerplate.models.User;
import java.util.List;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@TestConfiguration
public class SpringSecurityUserProvider {
  @Bean
  public User testUser() {
    return User.builder()
        .email("username@example.com")
        .password("password")
        .role(Role.ROLE_USER)
        .build();
  }

  @Bean
  @Primary
  public UserDetailsService userDetailsService() {

    User userDetails = testUser();

    return new UserDetailsManager() {

      private final InMemoryUserDetailsManager inMemoryUserDetailsManager =
          new InMemoryUserDetailsManager(List.of(userDetails));

      @Override
      public void createUser(UserDetails userDetails) {
        this.inMemoryUserDetailsManager.createUser(userDetails);
      }

      @Override
      public void updateUser(UserDetails userDetails) {
        this.inMemoryUserDetailsManager.updateUser(userDetails);
      }

      @Override
      public void deleteUser(String s) {
        this.inMemoryUserDetailsManager.deleteUser(s);
      }

      @Override
      public void changePassword(String s, String s1) {
        this.inMemoryUserDetailsManager.changePassword(s, s1);
      }

      @Override
      public boolean userExists(String s) {
        return this.inMemoryUserDetailsManager.userExists(s);
      }

      @Override
      public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userDetails;
      }
    };
  }
}
