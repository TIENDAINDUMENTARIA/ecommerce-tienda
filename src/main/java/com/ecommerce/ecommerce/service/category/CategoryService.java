package com.ecommerce.ecommerce.service.category;

import java.util.List;

import com.ecommerce.ecommerce.dto.category.CategoryCreateDto;
import com.ecommerce.ecommerce.dto.category.CategoryDto;

public interface CategoryService {
    CategoryDto createCategory(CategoryCreateDto categoryCreateDto);
    CategoryDto getCategoryById(Long id);
    List<CategoryDto> getAllCategories();
    void deleteCategory(Long id);
    CategoryDto updateCategory(Long id, CategoryCreateDto categoryCreateDto);
}
