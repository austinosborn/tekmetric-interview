package com.interview.product.adapter.in.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record UpdateProductRequest(
    @Schema(description = "Name of the product", example = "Spark Plug")
    String name,
    @Size(max = 1024)
    @Schema(description = "Description of the product", example = "Premium synthetic engine oil for high-performance engines.")
    String description,
    @Min(0)
    @Schema(description = "Unit price of the product", example = "29.99")
    @Digits(integer = 19, fraction = 2)
    BigDecimal price,
    @Min(0)
    @Schema(description = "Unit quantity eligible for purchase", example = "150")
    Integer availableQuantity
) {

}
