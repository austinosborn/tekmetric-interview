package com.interview.cart.adapter.in.api.payload;

import com.interview.product.adapter.in.api.payload.ProductResponse;
import java.math.BigDecimal;

public record CartItemResponse(
    String id,
    ProductResponse product,
    int quantityInCart,
    BigDecimal subtotalForItem
){

}
