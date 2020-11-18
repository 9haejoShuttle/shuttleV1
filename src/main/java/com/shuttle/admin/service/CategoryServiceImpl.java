package com.shuttle.admin.service;

import com.shuttle.admin.domain.Category;
import com.shuttle.admin.dto.CategoryDto;
import com.shuttle.admin.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Long saveCategory(CategoryDto categorySaveRequestDto) {
        return categoryRepository.save(categorySaveRequestDto.toEntity()).getId();
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public List<Category> getCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories;
    }

    @Override
    public void updateCategory(CategoryDto categoryDto, Long id) {
        Category updateTargetCatgory = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id+"번 카테고리가 존재하지 않습니다."));

//        생성자로는 업데이트가 안 되고 따로 정의한 메서드로만 업데이트가 되는 이유가 뭘까?
//        updateTargetCatgory = Category.builder()
//                .categoryName(categoryDto.getCategoryName())
//                .memo(categoryDto.getMemo())
//                .build();

        updateTargetCatgory.update(categoryDto.getCategoryName(), categoryDto.getMemo());
    }
}
