package com.interview.cart.adapter.in.api;

import com.interview.cart.adapter.in.api.payload.CartAddProductRequest;
import com.interview.cart.adapter.in.api.payload.CartResponse;
import com.interview.cart.adapter.in.api.payload.CartUpdateRequest;
import com.interview.cart.domain.service.CartApiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
@RequiredArgsConstructor
public class CartController {

  private static final String BASE_V1_CART = "/api/v1/cart";
  private static final String BASE_V1_CART_ITEM = "/api/v1/cart-items";

  private final CartApiService cartApiService;

  @GetMapping(BASE_V1_CART)
  public ResponseEntity<CartResponse> getCurrentCart() {
    return ResponseEntity.ok(cartApiService.getCurrentCart());
  }

  @PostMapping(BASE_V1_CART_ITEM)
  public ResponseEntity<CartResponse> addProductToCart(@Valid @RequestBody CartAddProductRequest request) {
    return ResponseEntity.ok(cartApiService.addProductToCart(request));
  }

  @PatchMapping(BASE_V1_CART_ITEM + "/{id}")
  public ResponseEntity<CartResponse> updateItemQuantity(@PathVariable(name = "id") String cartItemExternalId,
      @RequestBody CartUpdateRequest updateCartRequest) {
    return ResponseEntity.ok(cartApiService.updateItemInCart(cartItemExternalId, updateCartRequest));
  }

  @DeleteMapping(BASE_V1_CART_ITEM + "/{id}")
  public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable(name = "id") String cartItemExternalId) {
    return ResponseEntity.ok(cartApiService.removeCartItem(cartItemExternalId));
  }

  @PostMapping("/checkout")
  public ResponseEntity<CartResponse> checkout() {
    CartResponse itemsPurchased = cartApiService.checkout();
    return ResponseEntity.ok(itemsPurchased);
  }
}
