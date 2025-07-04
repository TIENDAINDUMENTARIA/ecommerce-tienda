package com.ecommerce.ecommerce.mappers.category;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.ecommerce.ecommerce.domain.Category;
import com.ecommerce.ecommerce.dto.category.CategoryCreateDto;
import com.ecommerce.ecommerce.dto.category.CategoryDto;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    @Mapping(target = "products", ignore = true)
    @Mapping(target = "id_category", ignore = true)
    Category categoryCreateDtoToCategory(CategoryCreateDto categoryCreateDto);

    CategoryDto categoryToCategoryDto(Category category);
}
