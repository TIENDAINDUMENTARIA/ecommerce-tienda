package com.ecommerce.ecommerce.service.order;

import java.util.List;

import com.ecommerce.ecommerce.domain.enums.StatusEnumOrder;
import com.ecommerce.ecommerce.dto.order.OrderCreateDto;
import com.ecommerce.ecommerce.dto.order.OrderDto;

public interface OrderService {
    // Crear un nuevo pedido
    OrderDto createOrder(OrderCreateDto orderCreateDTO);

    // Obtener un pedido por ID
    OrderDto getOrderById(Long id);

    // Obtener todos los pedidos de un usuario
    List<OrderDto> getOrdersByUserId();

    // Cancelar un pedido
    void cancelOrder(Long id);

    void updateOrderStatus(Long id, StatusEnumOrder newStatus);
}
