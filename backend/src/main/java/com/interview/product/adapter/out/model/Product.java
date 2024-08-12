package com.interview.product.adapter.out.model;

import jakarta.persistence.*;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Getter
@Setter
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(updatable = false, nullable = false)
  @Setter(AccessLevel.NONE)
  private String externalId = UUID.randomUUID().toString();

  private String name;

  private String description;

  private BigDecimal price;

  private int availableQuantity;

  public void reduceAvailableQuantity(int quantityPurchased) {
    setAvailableQuantity(getAvailableQuantity() - quantityPurchased);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Product product = (Product) o;
    return Objects.equals(id, product.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
