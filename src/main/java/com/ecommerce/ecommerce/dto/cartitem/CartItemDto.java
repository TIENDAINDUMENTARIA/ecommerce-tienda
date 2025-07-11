package com.ecommerce.ecommerce.dto.cartitem;

import com.ecommerce.ecommerce.dto.product.ProductDto;

public record CartItemDto(
    // ID del ítem del carrito
    Long id_cart_item,

    // Producto asociado
    ProductDto product,

    // Cantidad de productos
    Integer quantity,

    // Precio unitario
    Double unitPrice
) {

}
