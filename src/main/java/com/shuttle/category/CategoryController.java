package com.shuttle.category;

import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/api/category")
    public void addCategory(@RequestBody CategoryDto categoryDto, Model model) {
        categoryService.saveCategory(categoryDto);
    }

    @DeleteMapping("/api/category/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }


    @PutMapping("/api/category/{id}")
    public Long updateCategory(@RequestBody CategoryDto categoryDto,@PathVariable Long id) {
        categoryService.updateCategory(categoryDto, id);
        return id;
    }

}
