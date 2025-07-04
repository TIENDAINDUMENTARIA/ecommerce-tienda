package com.ecommerce.ecommerce.dto.product;

import java.util.List;

public record ProductDto(
    Long id_product,
    String name,
    String description,
    List<String> imageUrls,
    Double price,
    Integer stock,
    String categoryName
) {

}