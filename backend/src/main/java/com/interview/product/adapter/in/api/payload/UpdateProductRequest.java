package com.interview.product.adapter.in.api.payload;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequest(
    String name,
    @Size(max = 1024)
    String description,
    @Min(0)
    BigDecimal price,
    @Min(0)
    Integer availableQuantity
) {

}
