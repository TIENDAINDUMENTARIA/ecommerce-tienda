package com.ecommerce.ecommerce.dto.orderitem;

import com.ecommerce.ecommerce.dto.product.ProductDto;

public record OrderItemDto(
    // ID del ítem del pedido
    Long id_order_item,

    // Producto asociado
    ProductDto product,

    // Cantidad de productos
    Integer quantity,

    // Precio unitario
    Double unitPrice
) {

}
