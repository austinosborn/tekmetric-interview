package com.interview.cart.adapter.out.model;

import com.interview.product.adapter.out.model.Product;
import com.interview.user.adapter.out.model.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cart_items")
@Getter
@NoArgsConstructor
public class CartItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(updatable = false, nullable = false)
  @Setter(AccessLevel.NONE)
  private String externalId = UUID.randomUUID().toString();

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  @Column(nullable = false)
  private int quantity;

  @Column(nullable = false)
  private BigDecimal totalPrice;

  public CartItem(User user, Product product, int quantity) {
    this.user = user;
    this.product = product;
    this.quantity = quantity;
    updateTotalPrice();
  }

  @PrePersist
  @PreUpdate
  public void updateTotalPrice() {
    if (product != null) {
      this.totalPrice = product.getPrice().multiply(BigDecimal.valueOf(quantity));
    }
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
    updateTotalPrice();
  }
}
