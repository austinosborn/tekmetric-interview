package com.interview.cart.adapter.in.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

public record CartUpdateRequest(
    @Schema(description = "Quantity of product within cart", minimum = "1")
    @Min(1)
    int quantity
) {

}
