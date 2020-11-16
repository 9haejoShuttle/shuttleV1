package com.shuttle.admin.controller;

import com.shuttle.admin.dto.CategoryDto;
import com.shuttle.admin.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/api/category")
    public void addCategory(@RequestBody CategoryDto categorySaveRequestDto, Model model) {
        categoryService.saveCategory(categorySaveRequestDto);
    }

    @DeleteMapping("/api/category/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }

/*
    사용할 필요가 있다면 CategoryUpdateRequestDto를 따로 만들어야 할 것 같다.
    update할 때는 id를 확인하는 필드가 필요한데 SaveDto에는 id필드가 없다.
    @PutMapping("/category/{id}")
    public void updateCategory(@RequestBody CategorySaveRequestDto categorySaveRequestDto) {
        categoryService.saveCategory(categorySaveRequestDto);
    }*/



}
