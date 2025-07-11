package com.ecommerce.ecommerce.dto.orderitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderItemCreateDto(
    @NotNull(message = "El ID del producto es obligatorio")
    // ID del producto
    Long productId,

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que 0")
    // Cantidad de productos
    Integer quantity,

    @NotNull(message = "El precio unitario es obligatorio")
    @Positive(message = "El precio unitario debe ser mayor que 0")
    // Precio unitario del producto
    Double unitPrice
) {

}
