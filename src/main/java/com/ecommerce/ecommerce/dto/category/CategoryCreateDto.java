package com.ecommerce.ecommerce.dto.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CategoryCreateDto(
    @NotNull(message = "The category name cannot be null.")
    @NotBlank(message = "The category name cannot be empty.")
    String name
) {

}