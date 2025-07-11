package com.ecommerce.ecommerce.dto.cartitem;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CartItemCreateDto(
    @NotNull(message = "El ID del producto es obligatorio")
    // ID del producto
    Long productId,

    @NotNull(message = "La cantidad es obligatoria")
    @Positive(message = "La cantidad debe ser mayor que 0")
    // Cantidad de productos
    Integer quantity
) {

}
