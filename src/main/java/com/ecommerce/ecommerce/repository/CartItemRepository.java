package com.ecommerce.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.domain.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
