package com.interview.product.adapter.in.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.math.BigDecimal;

public record ProductResponse(
    @Schema(description = "Id of the product", example = "a3e6d3a4-01e8-4d88-a6b3-5b1f3f1c0e4b", requiredMode = RequiredMode.REQUIRED)
    String id,
    @Schema(description = "Name of the product", example = "Engine Oil", requiredMode = RequiredMode.REQUIRED)
    String name,
    @Schema(description = "Description of the product", example = "Premium synthetic engine oil for high-performance engines.")
    String description,
    @Schema(description = "Unit price of the product", example = "29.99")
    BigDecimal price,
    @Schema(description = "Unit quantity eligible for purchase", example = "150")
    int availableQuantity
) {

}
