package com.interview.product.adapter.in.api;

import com.interview.product.adapter.in.api.payload.CreateProductRequest;
import com.interview.product.adapter.in.api.payload.ProductResponse;
import com.interview.product.adapter.in.api.payload.UpdateProductRequest;
import com.interview.product.domain.service.ProductApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "ProductController", description = "Provides API for all users to browse available products, and for shop owners to maintain their product catalogue.")
public class ProductController {

  private static final String BASE_V1 = "/api/v1/products";

  private final ProductApiService productApiService;

  @Operation(summary = "Create Product", description = "Create a new product. Must be signed in with SHOP_OWNER user to perform operation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Forbidden. You do not have the proper role to perform this operation.", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @PostMapping(BASE_V1)
  public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
    return ResponseEntity.ok(productApiService.createNewProduct(request));
  }

  @Operation(summary = "Update Product", description = "Updates a existing product. Only values specified in request are updated. Must be signed in with SHOP_OWNER user to perform operation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Forbidden. You do not have the proper role to perform this operation.", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @PatchMapping(BASE_V1 + "/{id}")
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name = "id") String productExternalId, @Valid @RequestBody UpdateProductRequest request) {
    return ResponseEntity.ok(productApiService.updateProduct(productExternalId, request));
  }

  @Operation(summary = "Delete Product", description = "Delete an existing product. Returns true if deleted, false if requested product was not present. Must be signed in with SHOP_OWNER user to perform operation.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Forbidden. You do not have the proper role to perform this operation.", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @DeleteMapping(BASE_V1 + "/{id}")
  public ResponseEntity<Boolean> deleteProduct(@PathVariable(name = "id") String productExternalId) {
    return ResponseEntity.ok(productApiService.deleteProduct(productExternalId));
  }

  @Operation(summary = "Get Product Catalogue", description = "Returns all products in the shop catalogue.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @GetMapping(BASE_V1)
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    return ResponseEntity.ok(productApiService.getAllProducts());
  }

  @Operation(summary = "Get Product", description = "Returns the product specified.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Success"),
      @ApiResponse(responseCode = "400", description = "Request is malformed", content = @Content(schema = @Schema(implementation = String.class))),
      @ApiResponse(responseCode = "401", description = "Unauthorized. JWT provided is invalid", content = @Content(schema = @Schema(implementation = String.class)))
  })
  @GetMapping(BASE_V1 + "/{id}")
  public ResponseEntity<ProductResponse> getProduct(@PathVariable(name = "id") String productExternalId) {
    return ResponseEntity.ok(productApiService.getProduct(productExternalId));
  }
}
