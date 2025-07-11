package com.ecommerce.ecommerce.dto.cart;

import jakarta.validation.constraints.NotNull;

public record CartCreateDto(
    @NotNull(message = "El ID del usuario es obligatorio")
    Long id_user
) {

}
