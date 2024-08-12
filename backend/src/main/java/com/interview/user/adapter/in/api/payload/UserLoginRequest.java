package com.interview.user.adapter.in.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
    @NotBlank(message = "email must not be blank!")
    @Schema(description = "email address of the user", example = "johnny.supplier@example.com", requiredMode = RequiredMode.REQUIRED)
    String email,
    @NotBlank(message = "password must not be blank!")
    @Schema(description = "password of the user", example = "P@ssword123", requiredMode = RequiredMode.REQUIRED)
    String password
) {

}
