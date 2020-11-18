package com.shuttle.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.admin.domain.Category;
import com.shuttle.admin.dto.CategoryDto;
import com.shuttle.admin.repository.CategoryRepository;
import com.shuttle.admin.service.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@SpringBootTest
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void deleteAll() {
        categoryRepository.deleteAll();
    }

    @DisplayName("카테고리 등록")
    @Test
    void test_addCategory() throws Exception {
        CategoryDto dto = CategoryDto.builder()
                .categoryName("자유 게시판")
                .memo("로그인한 유저만 작성 가능")
                .build();

        mockMvc.perform(post("/api/category")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(dto)))
                .andExpect(status().isOk());

        Category addedCategory = categoryRepository.findAll().get(0);

        String categoryName = addedCategory.getCategoryName();
        String categoryMemo = addedCategory.getMemo();

        assertEquals(categoryName, dto.getCategoryName());
        assertEquals(categoryMemo, dto.getMemo());

    }

    @DisplayName("카테고리 수정")
    @Test
    void test_update_category() throws Exception {
        CategoryDto updateRequestDto = CategoryDto.builder()
                .categoryName("공지사항")
                .memo("자유 게시판에서 공지사항으로 이름 수정")
                .build();

        Category updateTargetCategory = addCategory();

        mockMvc.perform(put("/api/category/"+updateTargetCategory.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
                .andExpect(status().isOk());

        Category updatedCategory = categoryRepository.findById(updateTargetCategory.getId()).get();

        assertThat(updatedCategory.getCategoryName())
                .isEqualTo(updateRequestDto.getCategoryName());
        assertThat(updatedCategory.getMemo())
                .isEqualTo(updateRequestDto.getMemo());
    }

    @DisplayName("카테고리 삭제")
    @Test
    void test_delete_category() throws Exception {
        Category newCategory = addCategory();

        assertThat(categoryRepository.findById(newCategory.getId())).isNotEmpty();

        mockMvc.perform(delete("/api/category/"+newCategory.getId()))
                .andExpect(status().isOk());

        assertThat(categoryRepository.findById(newCategory.getId())).isEmpty();
    }

    Category addCategory() {
        CategoryDto dto = CategoryDto.builder()
                .categoryName("자유 게시판")
                .memo("로그인한 유저만 작성 가능")
                .build();

        Category newCategory = categoryRepository.save(dto.toEntity());
        return newCategory;
    }
}