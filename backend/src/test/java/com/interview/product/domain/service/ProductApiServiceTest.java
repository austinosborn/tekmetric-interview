package com.interview.product.domain.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.interview.app.exception.NotFoundException;
import com.interview.product.adapter.in.api.payload.CreateProductRequest;
import com.interview.product.adapter.in.api.payload.ProductResponse;
import com.interview.product.adapter.in.api.payload.UpdateProductRequest;
import com.interview.product.adapter.out.model.Product;
import com.interview.product.adapter.out.model.ProductRepository;
import com.interview.user.domain.UserVerification;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.hamcrest.MatcherAssert;
import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductApiServiceTest {

  private static final String PRODUCT_EXTERNAL_ID = "productExternalId";
  private static final String PRODUCT_NAME = "productName";
  private static final String PRODUCT_DESCRIPTION = "productDescription";
  private static final BigDecimal PRICE = BigDecimal.valueOf(49.99);
  private static final int AVAILABLE_QUANTITY = 50;

  @Mock
  private ProductMapper productMapper;
  @Mock
  private ProductRepository productRepository;
  @Mock
  private Product product;
  @Mock
  private ProductResponse productResponse;
  @Mock
  private CreateProductRequest createProductRequest;
  @Mock
  private UpdateProductRequest updateProductRequest;
  @Mock
  private UserVerification userVerification;
  @InjectMocks
  private ProductApiService productApiService;


  @Test
  void createNewProduct() {
    when(productMapper.toProduct(createProductRequest)).thenReturn(product);
    when(productRepository.save(product)).thenReturn(product);
    when(productMapper.toProductResponse(product)).thenReturn(productResponse);

    ProductResponse result = productApiService.createNewProduct(createProductRequest);

    verify(userVerification).verifyUserIsShopOwner();
    assertEquals(productResponse, result);
  }

  @Test
  void updateProduct_ProductNotFound() {
    when(productRepository.findByExternalId(PRODUCT_EXTERNAL_ID)).thenReturn(Optional.empty());

    NotFoundException exception = assertThrows(NotFoundException.class, () -> productApiService.updateProduct(PRODUCT_EXTERNAL_ID, updateProductRequest));

    assertEquals("Product not found with ID: " + PRODUCT_EXTERNAL_ID, exception.getMessage());
  }

  @Test
  void updateProduct_CorrectlySetsFieldsThatAreSpecified() {
    when(productRepository.findByExternalId(PRODUCT_EXTERNAL_ID)).thenReturn(Optional.of(product));
    when(updateProductRequest.name()).thenReturn(PRODUCT_NAME);
    when(updateProductRequest.description()).thenReturn(PRODUCT_DESCRIPTION);
    when(updateProductRequest.price()).thenReturn(PRICE);
    when(updateProductRequest.availableQuantity()).thenReturn(AVAILABLE_QUANTITY);
    when(productMapper.toProductResponse(product)).thenReturn(productResponse);

    ProductResponse result = productApiService.updateProduct(PRODUCT_EXTERNAL_ID, updateProductRequest);

    verify(userVerification).verifyUserIsShopOwner();
    assertEquals(productResponse, result);
    verify(product).setName(PRODUCT_NAME);
    verify(product).setDescription(PRODUCT_DESCRIPTION);
    verify(product).setPrice(PRICE);
    verify(product).setAvailableQuantity(AVAILABLE_QUANTITY);
  }

  @Test
  void updateProduct_DoesNotUpdateFieldsNotSpecified() {
    when(productRepository.findByExternalId(PRODUCT_EXTERNAL_ID)).thenReturn(Optional.of(product));
    when(updateProductRequest.name()).thenReturn(null);
    when(updateProductRequest.description()).thenReturn(null);
    when(updateProductRequest.price()).thenReturn(null);
    when(updateProductRequest.availableQuantity()).thenReturn(null);
    when(productMapper.toProductResponse(product)).thenReturn(productResponse);

    ProductResponse result = productApiService.updateProduct(PRODUCT_EXTERNAL_ID, updateProductRequest);

    verify(userVerification).verifyUserIsShopOwner();
    assertEquals(productResponse, result);
    verify(product, never()).setName(any());
    verify(product, never()).setDescription(any());
    verify(product, never()).setPrice(any());
    verify(product, never()).setAvailableQuantity(anyInt());
  }

  @Test
  void deleteProduct_NotFound_ShouldReturnFalse() {
    when(productRepository.deleteByExternalId(PRODUCT_EXTERNAL_ID)).thenReturn(0);

    boolean result = productApiService.deleteProduct(PRODUCT_EXTERNAL_ID);

    assertFalse(result);
  }

  @Test
  void deleteProduct_Found_ShouldReturnFalse() {
    when(productRepository.deleteByExternalId(PRODUCT_EXTERNAL_ID)).thenReturn(1);

    boolean result = productApiService.deleteProduct(PRODUCT_EXTERNAL_ID);

    assertTrue(result);
  }

  @Test
  void getAllProducts() {
    when(productRepository.findAll()).thenReturn(List.of(product));
    when(productMapper.toProductResponse(product)).thenReturn(productResponse);

    List<ProductResponse> result = productApiService.getAllProducts();

    MatcherAssert.assertThat(result, IsIterableContainingInAnyOrder.containsInAnyOrder(productResponse));
  }

  @Test
  void getProduct() {
    when(productRepository.findByExternalId(PRODUCT_EXTERNAL_ID)).thenReturn(Optional.of(product));
    when(productMapper.toProductResponse(product)).thenReturn(productResponse);

    ProductResponse result = productApiService.getProduct(PRODUCT_EXTERNAL_ID);

    assertEquals(productResponse, result);
  }

  @Test
  void getProduct_NotFound() {
    when(productRepository.findByExternalId(PRODUCT_EXTERNAL_ID)).thenReturn(Optional.empty());

    NotFoundException exception = assertThrows(NotFoundException.class, () -> productApiService.getProduct(PRODUCT_EXTERNAL_ID));

    assertEquals("Product not found with ID: " + PRODUCT_EXTERNAL_ID, exception.getMessage());
  }
}