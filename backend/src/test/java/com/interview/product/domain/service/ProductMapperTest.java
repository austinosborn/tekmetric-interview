package com.interview.product.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.interview.product.adapter.in.api.payload.CreateProductRequest;
import com.interview.product.adapter.in.api.payload.ProductResponse;
import com.interview.product.adapter.out.model.Product;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductMapperTest {

  private static final String UUID_PATTERN = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$";

  private static final String PRODUCT_EXTERNAL_ID = "productExternalId";
  private static final String PRODUCT_NAME = "productName";
  private static final String PRODUCT_DESCRIPTION = "productDescription";
  private static final BigDecimal PRICE = BigDecimal.valueOf(49.99);
  private static final int AVAILABLE_QUANTITY = 50;

  @Mock
  private CreateProductRequest createProductRequest;
  @Mock
  private Product product;
  @InjectMocks
  private ProductMapperImpl productMapper;

  @Test
  void toProduct() {
    when(createProductRequest.name()).thenReturn(PRODUCT_NAME);
    when(createProductRequest.description()).thenReturn(PRODUCT_DESCRIPTION);
    when(createProductRequest.price()).thenReturn(PRICE);
    when(createProductRequest.availableQuantity()).thenReturn(AVAILABLE_QUANTITY);

    Product product = productMapper.toProduct(createProductRequest);

    assertEquals(PRODUCT_NAME, product.getName());
    assertEquals(PRODUCT_DESCRIPTION, product.getDescription());
    assertEquals(PRICE, product.getPrice());
    assertEquals(AVAILABLE_QUANTITY, product.getAvailableQuantity());
    assertTrue(product.getCartItems().isEmpty());
    assertNotNull(product.getExternalId());
    assertTrue(product.getExternalId().matches(UUID_PATTERN));
  }

  @Test
  void toProductResponse() {
    when(product.getName()).thenReturn(PRODUCT_NAME);
    when(product.getDescription()).thenReturn(PRODUCT_DESCRIPTION);
    when(product.getPrice()).thenReturn(PRICE);
    when(product.getAvailableQuantity()).thenReturn(AVAILABLE_QUANTITY);
    when(product.getExternalId()).thenReturn(PRODUCT_EXTERNAL_ID);

    ProductResponse productResponse = productMapper.toProductResponse(product);

    assertEquals(PRODUCT_EXTERNAL_ID, productResponse.id());
    assertEquals(PRODUCT_NAME, productResponse.name());
    assertEquals(PRODUCT_DESCRIPTION, productResponse.description());
    assertEquals(PRICE, productResponse.price());
    assertEquals(AVAILABLE_QUANTITY, productResponse.availableQuantity());
  }
}