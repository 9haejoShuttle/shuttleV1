package com.shuttle.admin.controller;

import com.shuttle.admin.domain.Category;
import com.shuttle.admin.domain.Post;
import com.shuttle.admin.dto.CategoryDto;
import com.shuttle.admin.dto.PostSaveRequestDto;
import com.shuttle.admin.repository.CategoryRepository;
import com.shuttle.admin.repository.PostRepository;
import com.shuttle.admin.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private PostService postService;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void addCategory() {
        Category category = Category.builder()
                .categoryName("자유 게시판")
                .memo("메모 없음")
                .build();
        categoryRepository.save(category);
        assertThat(categoryRepository.findById(1L)).isNotEmpty();
    }

    @DisplayName("게시물 등록 및 조회")
    @Test
    void test_addPost() throws Exception {
        Category category = categoryRepository.findById(1L).get();

        PostSaveRequestDto saveRequest = PostSaveRequestDto.builder()
                .category(category)
                .title("yo title")
                .content("yo text")
                .userId(1L)
                .build();

        postService.postSave(saveRequest);

        Post newPost = postRepository.findById(1L)
                .orElseThrow(() ->new IllegalArgumentException("해당 게시물이 없습니다."));

        assertThat(newPost.getId())
                .isNotNull();
        assertThat(newPost.getCategory().getCategoryName())
                .isEqualTo(saveRequest.getCategory().getCategoryName());
        assertThat(newPost.getTitle())
                .isEqualTo(saveRequest.getTitle());
        assertThat(newPost.getUserId())
                .isEqualTo(saveRequest.getUserId());
    }
}