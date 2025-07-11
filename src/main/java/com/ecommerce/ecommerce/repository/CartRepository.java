package com.ecommerce.ecommerce.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.ecommerce.domain.Cart;

public interface CartRepository extends JpaRepository<Cart, Long>{
    // Buscar carrito por ID de usuario con sus ítems
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items i LEFT JOIN FETCH i.product p WHERE c.user.id_user = :userId")
    Optional<Cart> findByUserId(Long userId);
}
