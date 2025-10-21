package com.archivos.ecommerce.repositories;

import com.archivos.ecommerce.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCart_CartId(UUID cartCartId);
}
