package com.interview.user.domain;

import com.interview.app.exception.UnauthorizedException;
import com.interview.app.config.JwtUtil;
import com.interview.user.adapter.in.api.payload.UserLoginResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthApiService {

  private final AuthenticationProvider authenticationProvider;
  private final CurrentUserDetailsService userDetailsService;
  private final JwtUtil jwtUtil;

  public UserLoginResponse login(String email, String password) {
    try {
      authenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password));

    } catch (BadCredentialsException e) {
      throw new UnauthorizedException("Incorrect login information!");
    }

    UserDetails userDetails = userDetailsService.loadUserByUsername(email);
    String jwt = jwtUtil.generateToken(userDetails);

    return new UserLoginResponse(jwt);
  }

}
