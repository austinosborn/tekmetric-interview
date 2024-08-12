package com.interview.user.adapter.out.model;

import com.interview.cart.adapter.out.model.CartItem;
import com.interview.product.adapter.out.model.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Setter(AccessLevel.NONE)
  private Long id;

  @Column(updatable = false, nullable = false)
  @Setter(AccessLevel.NONE)
  private String externalId = UUID.randomUUID().toString();

  @Column(nullable = false)
  private String password;

  private String firstName;

  private String lastName;

  @Column(unique = true)
  private String email;

  private boolean enabled = true;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(
      name = "user_roles",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id")
  )
  private Set<Role> roles = new HashSet<>();

  @OneToMany(mappedBy = "user")
  private List<CartItem> cartItems = new ArrayList<>();

  public Optional<CartItem> findItemInCart(Product product) {
    return cartItems.stream().filter(cartItem -> cartItem.getProduct().getId().equals(product.getId())).findFirst();
  }

  public void addCartItem(CartItem cartItem) {
    this.cartItems.add(cartItem);
  }

  public void removeProductFromCart(Product product) {
    this.cartItems.removeIf(cartItem -> cartItem.getProduct().equals(product));
  }
}
