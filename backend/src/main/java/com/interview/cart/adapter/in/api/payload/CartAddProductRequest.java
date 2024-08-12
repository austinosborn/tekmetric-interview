package com.interview.cart.adapter.in.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CartAddProductRequest(
    @Schema(description = "ID of the product", example = "a3e6d3a4-01e8-4d88-a6b3-5b1f3f1c0e4b", requiredMode = RequiredMode.REQUIRED)
    @NotBlank
    String productId,
    @Schema(description = "Quantity of product to add to cart", example = "2", requiredMode = RequiredMode.REQUIRED, minimum = "1")
    @NotNull
    @Min(1)
    Integer quantity
) {

}
