package com.ecommerce.ecommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public record ProductCreateDto(
    @NotNull(message = "The product name cannot be null.")
    @NotBlank(message = "The product name cannot be empty.")
    String name,
    String description,
    String image,
    @NotNull(message = "The price cannot be null.")
    @Positive(message = "The product price must be greater than zero.")
    Double price,
    @NotNull(message = "The stock cannot be null.")
    @PositiveOrZero(message = "The product stock must be zero or greater.")
    Integer stock,
    @NotBlank(message = "The category name cannot be empty.")
    String name_category
) {

}