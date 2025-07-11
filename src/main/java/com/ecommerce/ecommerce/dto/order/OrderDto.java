package com.ecommerce.ecommerce.dto.order;

import java.time.LocalDateTime;
import java.util.List;

import com.ecommerce.ecommerce.domain.enums.StatusEnumOrder;
import com.ecommerce.ecommerce.dto.orderitem.OrderItemDto;


public record OrderDto(
    Long id_order,
    Long userId,
    LocalDateTime createdAt,
    StatusEnumOrder status,
    Double total,
    List<OrderItemDto> items

    // Pago asociado
    //PaymentDto payment
) {

}
