package com.shuttle.post;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.domain.Category;
import com.shuttle.domain.Post;
import com.shuttle.post.PostSaveRequestDto;
import com.shuttle.category.CategoryRepository;
import com.shuttle.post.PostRepository;
import com.shuttle.post.PostService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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

    @AfterEach
    void deleteAll() {  //테스트가 끝날 때마다 DB데이터 초기화
        postRepository.deleteAll();
    }

    @DisplayName("게시물 등록")
    @WithMockUser(roles = "USER")
    @Test
    void test_savePost() throws Exception {
        //게시물 등록을 요청하는 데이터
        PostSaveRequestDto saveRequest = PostSaveRequestDto.builder()
                .category(new Category("자유 게시판", "메모 없음"))
                .title("yo title")
                .content("yo text")
                .userId(1L)
                .build();

        /*
        *   등록 API호출
        *   가상 환경에서 post요청으로 등록을 처리하는 api를 호출
        *   ObjectMapper의 writeValueAsString()메서드를 이용해서 request 데이터를 JSON으로 전송한다.
        *   (요청 API가 있는 컨트롤러가 @RestController이기 때문에 JSON타입으로 요청해야 함)
        * */
        mockMvc.perform(post("/api/post")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(saveRequest)))
                .andExpect(status().isOk());

        //등록된 포스트 가져오기
        Post savedPost = postRepository.findAll().get(0);

        //등록된 포스트가, 요청 받은 데이터와 일치하는지 테스트
        assertThat(savedPost.getId())
                .isNotNull();
        assertThat(savedPost.getCategory().getCategoryName())
                .isEqualTo(saveRequest.getCategory().getCategoryName());
        assertThat(savedPost.getTitle())
                .isEqualTo(saveRequest.getTitle());
        assertThat(savedPost.getUserId())
                .isEqualTo(saveRequest.getUserId());
    }

    @DisplayName("게시물 조회")
    @Test
    void test_findByEmail() throws Exception {
        addPost(); //게시물 등록
        mockMvc.perform(get("/api/post/"+getId()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Transactional
    @DisplayName("게시물 수정")
    @WithMockUser(roles = "USER")
    @Test
    void test_update_post() throws Exception {
        addPost();  //게시물 등록

        Category category = categoryRepository.findAll().get(0);

        //수정 요청 데이터
        PostSaveRequestDto requestDto = PostSaveRequestDto.builder()
                .userId(1L)
                .category(category)
                .title("title update test")
                .content("content update")
                .build();

        /*
        *   put매핑 되어 있는 수정 API를 호출
        *   JSON타입 데이터를 보낸다고 명시하고,
        *   ObjectMapper의 wrtieValueAsString()로 요청 데이터를 JSON으로 변환해서 전달한다.
        * */
        mockMvc.perform(put("/api/post/"+getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());

        //업데이트된 포스트 불러오기
        Post updatedPost = postRepository.findById(getId()).get();

        System.out.println("-------------------------------------------------------");
        System.out.println("updatePost.getTitle  : " + updatedPost.getTitle());
        System.out.println("requestDto.getTitle  : " + requestDto.getTitle());
        System.out.println("-------------------------------------------------------");

        //업데이트 된 포스트와 업데이트 요청 받은 DTO의 데이터가 일치하는지 확인
        assertThat(updatedPost.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(updatedPost.getTitle()).isEqualTo(requestDto.getTitle());
        assertThat(updatedPost.getCategory().getCategoryName()).isEqualTo(requestDto.getCategory().getCategoryName());
        assertThat(updatedPost.getContent()).isEqualTo(requestDto.getContent());
    }

    @DisplayName("게시물 삭제")
    @WithMockUser(roles = "USER")
    @Test
    void test_delete_Post() throws Exception {
        addPost(); //게시물 등록
        assertThat(postRepository.findById(1L)).isNotEmpty();

        mockMvc.perform(delete("/api/post/"+1)
                .with(csrf()))
                .andExpect(status().isOk());

        assertThat(postRepository.findById(1L)).isEmpty();
    }

    Long getId() {
        return postRepository.findAll().get(0).getId();
    }

    void addPost() {
        PostSaveRequestDto saveRequest = PostSaveRequestDto.builder()
                .category(new Category("자유 게시판", "메모 없음"))
                .title("yo title")
                .content("yo text")
                .userId(1L)
                .build();
        postRepository.save(saveRequest.toEntity());
    }
}