package com.interview.user.adapter.in.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;

public record UserLoginResponse(
    @Schema(description = "Bearer token for access to application API. Expires as configured in server (current value 24hrs)")
    String bearer
) {

}
