package com.interview.app.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

  private static final String[] PUBLIC_PATHS = {
      "/api/welcome",
      "/h2-console/**",
      "/api/v1/auth/login",
      "/swagger-ui.html",
      "/swagger-ui/**",
      "/v3/api-docs/**"
  };

  private final JwtRequestFilter jwtRequestFilter;
  private final UserDetailsService userDetailsService;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests((requests) -> requests
            .requestMatchers(PUBLIC_PATHS).permitAll() //Resources explicitly not requiring auth
            .anyRequest().authenticated()// Anything else
        )
        //Handle cases where JWT token is missing or incorrect
        .exceptionHandling(exceptionHandling ->
            exceptionHandling.authenticationEntryPoint((request, response, authException) -> {
              response.setContentType("application/json");
              response.setStatus(HttpStatus.UNAUTHORIZED.value());
              response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"You must to provide a valid JWT bearer token.\"}");
            }))
        .sessionManagement((session) -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)  // Stateless session
        )
        .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService);
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }
}
