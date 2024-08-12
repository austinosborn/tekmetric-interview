package com.interview.product.adapter.in.api.payload;

import java.math.BigDecimal;

public record ProductResponse(
    String id,
    String name,
    String description,
    BigDecimal price,
    int availableQuantity
) {

}
