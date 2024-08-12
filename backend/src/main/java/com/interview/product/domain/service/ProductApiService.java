package com.interview.product.domain.service;

import com.interview.app.exception.NotFoundException;
import com.interview.product.adapter.in.api.payload.CreateProductRequest;
import com.interview.product.adapter.in.api.payload.ProductResponse;
import com.interview.product.adapter.in.api.payload.UpdateProductRequest;
import com.interview.product.adapter.out.model.Product;
import com.interview.product.adapter.out.model.ProductRepository;
import com.interview.user.domain.UserVerification;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductApiService {

  private final ProductMapper productMapper;
  private final ProductRepository productRepository;
  private final UserVerification userVerification;

  public ProductResponse createNewProduct(CreateProductRequest createProductRequest) {
    userVerification.verifyUserIsShopOwner();

    Product product = productMapper.toProduct(createProductRequest);
    product = productRepository.save(product);
    return productMapper.toProductResponse(product);
  }

  public ProductResponse updateProduct(String productExternalId, UpdateProductRequest updateProductRequest) {
    userVerification.verifyUserIsShopOwner();

    Product product = productRepository.findByExternalId(productExternalId)
        .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productExternalId));

    if (updateProductRequest.name() != null) {
      product.setName(updateProductRequest.name());
    }
    if (updateProductRequest.description() != null) {
      product.setDescription(updateProductRequest.description());
    }
    if (updateProductRequest.price() != null) {
      product.setPrice(updateProductRequest.price());
    }
    if (updateProductRequest.availableQuantity() != null) {
      product.setAvailableQuantity(updateProductRequest.availableQuantity());
    }

    return productMapper.toProductResponse(product);
  }

  public boolean deleteProduct(String productExternalId) {
    userVerification.verifyUserIsShopOwner();

    return productRepository.deleteByExternalId(productExternalId) > 0;
  }

  public List<ProductResponse> getAllProducts() {
    return productRepository.findAll().stream().map(productMapper::toProductResponse).toList();
  }

  public ProductResponse getProduct(String productExternalId) {
    return productMapper.toProductResponse(productRepository.findByExternalId(productExternalId)
        .orElseThrow(() -> new NotFoundException("Product not found with ID: " + productExternalId))
    );
  }
}
