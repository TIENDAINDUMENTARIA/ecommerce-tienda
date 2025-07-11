package com.ecommerce.ecommerce.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ecommerce.ecommerce.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{
    // Buscar pedidos por ID de usuario con ítems y pago
    @Query("SELECT o FROM Order o LEFT JOIN FETCH o.items oi LEFT JOIN FETCH oi.product")
    List<Order> findByUserId(Long userId);
}
