package com.interview.cart.adapter.in.api.payload;

import com.interview.product.adapter.in.api.payload.ProductResponse;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import java.math.BigDecimal;

public record CartItemResponse(
    @Schema(description = "ID of the CartItem", requiredMode = RequiredMode.REQUIRED, example = "fef6e5a1-4d93-4e35-9179-6e27e6e9f7da")
    String id,
    @Schema(description = "Product associated with the CartItem", requiredMode = RequiredMode.REQUIRED)
    ProductResponse product,
    @Schema(description = "Product quantity in cart", requiredMode = RequiredMode.REQUIRED, example = "5")
    int quantityInCart,
    @Schema(description = "Cost for product and quantity specified", requiredMode = RequiredMode.REQUIRED, example = "149.95")
    BigDecimal subtotalForItem
){

}
