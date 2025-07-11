package com.ecommerce.ecommerce.mappers.order;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.ecommerce.domain.Order;
import com.ecommerce.ecommerce.domain.OrderItem;
import com.ecommerce.ecommerce.dto.order.OrderDto;
import com.ecommerce.ecommerce.dto.orderitem.OrderItemDto;
import com.ecommerce.ecommerce.mappers.product.ProductMapper;

@Mapper(componentModel = "spring", uses = {ProductMapper.class})
public interface OrderMapper {
    // Mapear Order a OrderDTO
    @Mapping(target = "userId", source = "user.id_user")
    OrderDto orderToOrderDto(Order order);

    // Mapear OrderItem a OrderItemDTO
    OrderItemDto orderItemToOrderItemDTO(OrderItem orderItem);
}