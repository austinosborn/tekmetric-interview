package com.interview.product.adapter.in.api;

import com.interview.product.adapter.in.api.payload.CreateProductRequest;
import com.interview.product.adapter.in.api.payload.ProductResponse;
import com.interview.product.adapter.in.api.payload.UpdateProductRequest;
import com.interview.product.domain.service.ProductApiService;
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
public class ProductController {

  private static final String BASE_V1 = "/api/v1/products";

  private final ProductApiService productApiService;

  @PostMapping(BASE_V1)
  public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody CreateProductRequest request) {
    return ResponseEntity.ok(productApiService.createNewProduct(request));
  }

  @PatchMapping(BASE_V1 + "/{id}")
  public ResponseEntity<ProductResponse> updateProduct(@PathVariable(name = "id") String productExternalId, @Valid @RequestBody UpdateProductRequest request) {
    return ResponseEntity.ok(productApiService.updateProduct(productExternalId, request));
  }

  @DeleteMapping(BASE_V1 + "/{id}")
  public ResponseEntity<Boolean> deleteProduct(@PathVariable(name = "id") String productExternalId) {
    return ResponseEntity.ok(productApiService.deleteProduct(productExternalId));
  }

  @GetMapping(BASE_V1)
  public ResponseEntity<List<ProductResponse>> getAllProducts() {
    return ResponseEntity.ok(productApiService.getAllProducts());
  }

  @GetMapping(BASE_V1 + "/{id}")
  public ResponseEntity<ProductResponse> getProduct(@PathVariable(name = "id") String productExternalId) {
    return ResponseEntity.ok(productApiService.getProduct(productExternalId));
  }
}
