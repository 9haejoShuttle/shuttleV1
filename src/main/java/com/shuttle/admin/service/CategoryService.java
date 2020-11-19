package com.shuttle.admin.service;

import com.shuttle.admin.domain.Category;
import com.shuttle.admin.dto.CategoryDto;

import java.util.List;

public interface CategoryService {
    public Long saveCategory(CategoryDto categorySaveRequestDto);
    public void deleteCategory(Long id);
    public List<Category> getCategories();

    void updateCategory(CategoryDto categoryDto, Long id);
}

