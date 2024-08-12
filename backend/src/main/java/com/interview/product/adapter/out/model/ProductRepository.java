package com.interview.product.adapter.out.model;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Optional<Product> findByExternalId(String externalId);

  int deleteByExternalId(String externalId);
}
