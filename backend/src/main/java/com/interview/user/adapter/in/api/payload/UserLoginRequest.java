package com.interview.user.adapter.in.api.payload;

import jakarta.validation.constraints.NotBlank;

public record UserLoginRequest(
    @NotBlank(message = "email must not be blank!")
    String email,
    @NotBlank(message = "password must not be blank!")
    String password
) {

}
