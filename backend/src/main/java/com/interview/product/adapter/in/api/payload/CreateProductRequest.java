package com.interview.product.adapter.in.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record CreateProductRequest(
    @NotBlank(message = "name must be provided!")
    @Size(min = 1, max = 128)
    @Schema(description = "Name of the product", example = "Spark Plug", requiredMode = RequiredMode.REQUIRED, maxLength = 128)
    String name,
    @Size(max = 1024)
    @Schema(description = "Description of the product", example = "The description of the specific spark plug", maxLength = 1024)
    String description,
    @NotNull(message = "price is required!")
    @Min(0)
    @Digits(integer = 19, fraction = 2)
    @Schema(description = "Unit price of the product. Can use up to 2 decimals of precision.", example = "4.99", requiredMode = RequiredMode.REQUIRED)
    BigDecimal price,
    @NotNull(message = "quantity is required!")
    @Min(0)
    @Schema(description = "Unit quantity eligible for purchase", example = "150", requiredMode = RequiredMode.REQUIRED)
    int availableQuantity
) {

}
