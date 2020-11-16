package com.shuttle.admin.service;

import com.shuttle.admin.domain.Category;
import com.shuttle.admin.dto.CategoryDto;
import com.shuttle.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public void saveCategory(CategoryDto categorySaveRequestDto) {
        categoryRepository.save(categorySaveRequestDto.toEntity());
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public List<Category> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }
}
