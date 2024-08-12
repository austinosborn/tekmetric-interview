package com.interview.product.adapter.out.model;

import com.interview.cart.adapter.out.model.CartItem;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "product")
  private List<CartItem> cartItems = new ArrayList<>();

  public void reduceAvailableQuantity(int quantityPurchased) {
    setAvailableQuantity(getAvailableQuantity() - quantityPurchased);
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
    cartItems.forEach(CartItem::updateTotalPrice);
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
