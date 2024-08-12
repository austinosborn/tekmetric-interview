package com.interview.cart.adapter.in.api;

import com.interview.cart.adapter.in.api.payload.CartAddProductRequest;
import com.interview.cart.adapter.in.api.payload.CartResponse;
import com.interview.cart.adapter.in.api.payload.CartUpdateRequest;
import com.interview.cart.domain.service.CartApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "CartController", description = "Provides API for viewing a cart, adding/removing items from cart, and checking out.")
public class CartController {

  private static final String BASE_V1_CART = "/api/v1/cart";
  private static final String BASE_V1_CART_ITEM = "/api/v1/cart-items";

  private final CartApiService cartApiService;

  @Operation(summary = "Get Cart", description = "Returns the cart for the current authenticated user.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @GetMapping(BASE_V1_CART)
  public ResponseEntity<CartResponse> getCurrentCart() {
    return ResponseEntity.ok(cartApiService.getCurrentCart());
  }

  @Operation(summary = "Add product to cart", description =
      "Add a product to authenticated user's cart. If product already specified is already in cart, then the "
          + "quantity is updated to the value in the request. Must be signed in with BUYER user to perform operation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden. You do not have the proper role to perform this operation.", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @PostMapping(BASE_V1_CART_ITEM)
  public ResponseEntity<CartResponse> addProductToCart(@Valid @RequestBody CartAddProductRequest request) {
    return ResponseEntity.ok(cartApiService.addProductToCart(request));
  }

  @Operation(summary = "Update quantity of item in cart", description =
      "Update the quantity of a CartItem in the authenticated user's cart. Must be signed in with BUYER user to perform operation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "403", description = "Forbidden. You do not have the proper role to perform this operation.", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "404", description = "CartItem specified was not found.", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @PatchMapping(BASE_V1_CART_ITEM + "/{id}")
  public ResponseEntity<CartResponse> updateItemQuantity(@PathVariable(name = "id") @Parameter(description = "id of the CartItem") String cartItemExternalId,
      @RequestBody CartUpdateRequest updateCartRequest) {
    return ResponseEntity.ok(cartApiService.updateItemInCart(cartItemExternalId, updateCartRequest));
  }

  @Operation(summary = "Remove item from cart", description =
      "Removes the specified CartItem in the authenticated user's cart. If product already specified is already in cart, then the "
          + "quantity is updated to the value in the request. Must be signed in with BUYER user to perform operation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Forbidden. You do not have the proper role to perform this operation.", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "404", description = "CartItem specified was not found.", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @DeleteMapping(BASE_V1_CART_ITEM + "/{id}")
  public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable(name = "id") @Parameter(description = "id of the CartItem") String cartItemExternalId) {
    return ResponseEntity.ok(cartApiService.removeCartItem(cartItemExternalId));
  }

  @Operation(summary = "Checkout", description = "This operations performs a checkout for the current user. This should reflect a 'purchase' within the system. "
      + "Upon purchase, the respective quantities of each product in the cart are deducted from the product's availableQuantity. Cart must not be empty."
      + "Must be signed in with BUYER user to perform operation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Forbidden. You do not have the proper role to perform this operation.", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "409", description = "Cart was empty, or quantity specified was no longer available to purchase.", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @PostMapping(BASE_V1_CART + "/checkout")
  public ResponseEntity<CartResponse> checkout() {
    CartResponse itemsPurchased = cartApiService.checkout();
    return ResponseEntity.ok(itemsPurchased);
  }
}
