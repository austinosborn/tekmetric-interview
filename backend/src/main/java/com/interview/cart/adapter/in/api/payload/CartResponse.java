package com.interview.cart.adapter.in.api.payload;

import java.math.BigDecimal;
import java.util.List;

public record CartResponse(
    String userId,
    List<CartItemResponse> items,
    BigDecimal subtotal
){

}
