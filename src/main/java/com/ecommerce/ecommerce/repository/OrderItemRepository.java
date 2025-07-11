package com.ecommerce.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.ecommerce.domain.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>{

}
