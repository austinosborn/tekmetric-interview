package com.interview.product.domain.service;

import com.interview.product.adapter.in.api.payload.CreateProductRequest;
import com.interview.product.adapter.in.api.payload.ProductResponse;
import com.interview.product.adapter.out.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

  Product toProduct(CreateProductRequest createProductRequest);

  @Mapping(source = "externalId", target = "id")
  ProductResponse toProductResponse(Product product);
}
