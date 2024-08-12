package com.interview.cart.domain.service;

import com.interview.app.exception.ConflictException;
import com.interview.app.exception.NotFoundException;
import com.interview.cart.adapter.in.api.payload.CartAddProductRequest;
import com.interview.cart.adapter.in.api.payload.CartResponse;
import com.interview.cart.adapter.in.api.payload.CartResponseFactory;
import com.interview.cart.adapter.in.api.payload.CartUpdateRequest;
import com.interview.cart.adapter.out.model.CartItem;
import com.interview.cart.adapter.out.repo.CartItemRepository;
import com.interview.product.adapter.out.model.Product;
import com.interview.product.adapter.out.model.ProductRepository;
import com.interview.user.adapter.out.model.User;
import com.interview.user.domain.CurrentUserDetailsService;
import com.interview.user.domain.UserVerification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class CartApiService {

  private final UserVerification userVerification;
  private final CurrentUserDetailsService userDetailsService;
  private final ProductRepository productRepository;
  private final CartItemRepository cartItemRepository;
  private final CartResponseFactory cartResponseFactory;

  public CartResponse getCurrentCart() {
    User user = userDetailsService.getCurrentUser();
    userVerification.verifyUserIsBuyer();

    return cartResponseFactory.create(user.getExternalId(), user.getCartItems());
  }

  public CartResponse addProductToCart(CartAddProductRequest cartAddProductRequest) {
    User user = userDetailsService.getCurrentUser();
    userVerification.verifyUserIsBuyer();

    Product product = productRepository.findByExternalId(cartAddProductRequest.productId())
        .orElseThrow(() -> new NotFoundException("Cannot find product for ID: " + cartAddProductRequest.productId()));

    verifyRequestedQuantity(product, cartAddProductRequest.quantity());

    //If the product is already in the user's cart, update the quantity. Otherwise, create a new CartItem entry for it.
    user.findItemInCart(product)
        .ifPresentOrElse(match -> match.setQuantity(cartAddProductRequest.quantity()), () -> {
          CartItem cartItem = cartItemRepository.save(new CartItem(user, product, cartAddProductRequest.quantity()));
          user.addCartItem(cartItem);
        });

    return cartResponseFactory.create(user.getExternalId(), user.getCartItems());
  }

  public CartResponse removeCartItem(String cartItemExternalId) {
    User user = userDetailsService.getCurrentUser();
    userVerification.verifyUserIsBuyer();
    cartItemRepository.deleteByExternalId(cartItemExternalId);

    return cartResponseFactory.create(user.getExternalId(), user.getCartItems());
  }

  public CartResponse updateItemInCart(String cartItemExternalId, CartUpdateRequest updateRequest) {
    User user = userDetailsService.getCurrentUser();
    userVerification.verifyUserIsBuyer();

    CartItem cartItem = cartItemRepository.findByExternalId(cartItemExternalId)
        .orElseThrow(() -> new NotFoundException("Cannot find CartItem for ID: " + cartItemExternalId));

    verifyRequestedQuantity(cartItem.getProduct(), updateRequest.quantity());
    cartItem.setQuantity(updateRequest.quantity());

    return cartResponseFactory.create(user.getExternalId(), user.getCartItems());
  }

  public CartResponse checkout() {
    User user = userDetailsService.getCurrentUser();
    if (user.getCartItems().isEmpty()) {
      throw new ConflictException("You cannot checkout with an empty cart!");
    }

    //Verify all items are in stock, and reduce inventory as result of this purchase
    user.getCartItems().forEach(cartItem -> {
      verifyRequestedQuantity(cartItem.getProduct(), cartItem.getQuantity());
      cartItem.getProduct().reduceAvailableQuantity(cartItem.getQuantity());
    });

    //To expand, persist into Order, associate with user. For demo purposes, we conclude here by just returning the list of items purchased, and clearing the cart.
    CartResponse cartResponse = cartResponseFactory.create(user.getExternalId(), user.getCartItems());
    cartItemRepository.deleteAll(user.getCartItems());

    return cartResponse;
  }

  private void verifyRequestedQuantity(Product product, int requestedQuantity) {
    if (product.getAvailableQuantity() < requestedQuantity) {
      throw new ConflictException(
          String.format("There are only %d units of this product available! Product ID: %s", product.getAvailableQuantity(), product.getExternalId()));
    }
  }

}
