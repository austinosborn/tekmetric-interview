package com.interview.cart.adapter.in.api.payload;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
    @Schema(description = "ID of the user who has the Cart", requiredMode = RequiredMode.REQUIRED, example = "d5c58d9e-1a4d-4c2e-9a97-014ea23bb8f6")
    String userId,
    @Schema(description = "CartItems currently in the user's cart", requiredMode = RequiredMode.REQUIRED)
    List<CartItemResponse> items,
    @Schema(description = "Subtotal of all items in the user's cart", requiredMode = RequiredMode.REQUIRED, example = "149.95")
    BigDecimal subtotal
){

}
