package com.shuttle.admin.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
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
    void saveCategoryAndSavePost() {
        Category category = Category.builder()
                .categoryName("자유 게시판")
                .memo("메모 없음")
                .build();
        categoryRepository.save(category);

        assertThat(categoryRepository.findById(1L)).isNotEmpty();

        PostSaveRequestDto saveRequest = PostSaveRequestDto.builder()
                .category(category)
                .title("yo title")
                .content("yo text")
                .userId(1L)
                .build();
        postRepository.save(saveRequest.toEntity());
    }

    @DisplayName("게시물 등록")
    @Test
    void test_savePost() throws Exception {
        Category category = categoryRepository.findById(1L).get();

        PostSaveRequestDto saveRequest = PostSaveRequestDto.builder()
                .category(category)
                .title("yo title")
                .content("yo text")
                .userId(2L)
                .build();

        mockMvc.perform(post("/api/post")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(saveRequest)))
                .andExpect(status().isOk());

        Post newPost = postRepository.findById(2L)
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

    @DisplayName("게시물 조회")
    @Test
    void test_findByEmail() throws Exception {
        mockMvc.perform(get("/api/post/"+1))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Transactional
    @DisplayName("게시물 수정")
    @Test
    void test_update_post() throws Exception {
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .userId(1L)
                .category(categoryRepository.findById(1L).get())
                .title("title update test")
                .content("content update")
                .build();

        mockMvc.perform(put("/api/post/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        Post updatedPost = postRepository.findById(1L).get();

        System.out.println("-------------------------------------------------------");
        System.out.println("updatePost.getTitle  : " + updatedPost.getTitle());
        System.out.println("requestDto.getTitle  : " + requestDto.getTitle());
        System.out.println("-------------------------------------------------------");

        assertThat(updatedPost.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(updatedPost.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(updatedPost.getCategory().getCategoryName()).isEqualTo(requestDto.getCategory().getCategoryName());
        assertThat(updatedPost.getContent()).isEqualTo(requestDto.getContent());
    }

    @DisplayName("게시물 삭제")
    @Test
    void test_delete_Post() throws Exception {
        assertThat(postRepository.findById(1L)).isNotEmpty();

        mockMvc.perform(delete("/api/post/1"))
                .andExpect(status().isOk());

        assertThat(postRepository.findById(1L)).isEmpty();
    }
}