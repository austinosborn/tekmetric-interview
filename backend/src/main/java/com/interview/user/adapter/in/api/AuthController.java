package com.interview.user.adapter.in.api;

import com.interview.user.adapter.in.api.payload.UserLoginRequest;
import com.interview.user.adapter.in.api.payload.UserLoginResponse;
import com.interview.user.domain.AuthApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private static final String BASE_V1 = "/api/v1/auth/";
  private final AuthApiService authApiService;

  @PostMapping(BASE_V1 + "login")
  public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
    return ResponseEntity.ok(authApiService.login(request.email(), request.password()));
  }
}
