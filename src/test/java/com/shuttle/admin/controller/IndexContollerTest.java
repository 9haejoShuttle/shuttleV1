package com.shuttle.admin.controller;

import com.shuttle.admin.domain.Post;
import com.shuttle.admin.repository.PostRepository;
import com.shuttle.admin.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@AutoConfigureMockMvc
@SpringBootTest
class IndexContollerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void test_post_index() throws Exception {
        //테스트 게시물 등록
        for (int i = 0; i<10; i++) {
            Post post = Post.builder()
                    .userId((long)i)
                    .title("test " + i)
                    .content("text " + i)
                    .category(null)
                    .build();

            postRepository.save(post);
        }

        /*
        *   GET방식으로 '/admin/post' 호출
        *   'posts'라는 key를 가진 모델을 포함하는지,
        *   이용한 view가 '/admin/post/index'가 맞는지 테스트
        * */
        mockMvc.perform(get("/admin/post"))
                .andExpect(model().attributeExists("posts"))
                .andExpect(view().name("/admin/post/index"));
    }

}