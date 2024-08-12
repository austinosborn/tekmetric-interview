package com.interview.user.adapter.in.api;

import com.interview.app.exception.UnauthorizedException;
import com.interview.user.adapter.in.api.payload.UserLoginRequest;
import com.interview.user.adapter.in.api.payload.UserLoginResponse;
import com.interview.user.domain.AuthApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "Provides API for logging in (and would eventually support other auth type operations, such as resetting password "
    + "and email verification.")
public class AuthController {

  private static final String BASE_V1 = "/api/v1/auth/";
  private final AuthApiService authApiService;

  @Operation(summary = "Login", description = "Used to validate user login credentials, and provide a JWT token for use of application API")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. Login is incorrect", content = @Content(schema = @Schema(implementation = UnauthorizedException.class)))
  })
  @PostMapping(BASE_V1 + "login")
  public ResponseEntity<UserLoginResponse> login(@Valid @RequestBody UserLoginRequest request) {
    return ResponseEntity.ok(authApiService.login(request.email(), request.password()));
  }
}
