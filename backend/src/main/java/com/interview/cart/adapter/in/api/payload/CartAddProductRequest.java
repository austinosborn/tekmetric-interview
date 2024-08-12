package com.interview.cart.adapter.in.api.payload;

public record CartAddProductRequest(
    String productId,
    int quantity
) {

}
