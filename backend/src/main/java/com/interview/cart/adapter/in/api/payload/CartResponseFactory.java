package com.interview.cart.adapter.in.api.payload;

import com.interview.cart.adapter.out.model.CartItem;
import com.interview.product.domain.service.ProductMapper;
import java.math.BigDecimal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartResponseFactory {

  private final ProductMapper productMapper;

  public CartResponse create(String userId, List<CartItem> cartItems) {
    return new CartResponse(
        userId,
        cartItems.stream()
            .map(cartItem -> new CartItemResponse(cartItem.getExternalId(), productMapper.toProductResponse(cartItem.getProduct()), cartItem.getQuantity(),
                cartItem.getTotalPrice()))
            .toList(),
        cartItems.stream().map(CartItem::getTotalPrice).reduce(BigDecimal.ZERO, BigDecimal::add)
    );
  }

}
