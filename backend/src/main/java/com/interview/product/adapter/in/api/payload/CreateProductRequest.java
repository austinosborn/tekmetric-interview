package com.interview.product.adapter.in.api.payload;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record CreateProductRequest(
    @NotBlank(message = "name must be provided!")
    @Size(min = 1, max = 128)
    String name,
    @Size(max = 1024)
    String description,
    @NotNull(message = "price is required!")
    @Min(0)
    BigDecimal price,
    @NotNull(message = "quantity is required!")
    @Min(0)
    int availableQuantity
) {

}
