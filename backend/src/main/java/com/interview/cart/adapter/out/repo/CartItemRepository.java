package com.interview.cart.adapter.out.repo;

import com.interview.cart.adapter.out.model.CartItem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

  Optional<CartItem> findByExternalId(String externalId);

  void deleteByExternalId(String externalId);
}
