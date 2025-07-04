package com.ecommerce.ecommerce.dto.product;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public record ProductCreateMultipartDto(
    @NotBlank(message = "El nombre es obligatorio")
    String name,

    String description,

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    Double price,

    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    Integer stock,

    @NotBlank(message = "La categoría es obligatoria")
    String name_category,

    List<MultipartFile> images,

    List<String> existingImageUrls // Nuevo campo para URLs de imágenes existentes
) {
}