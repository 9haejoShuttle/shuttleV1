package com.shuttle.admin.repository;

import com.shuttle.admin.domain.Category;
import com.shuttle.admin.domain.Post;
import com.shuttle.admin.dto.PostSaveRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostRepositoryTest {

    @Autowired
    PostRepository postRepository;

    @DisplayName("findByDesc() 테스트")
    @Test
    void test_findByDesc() {
        Long targetId = 10L;
        for (int i = 0; i<10; i++) {
            Post post = Post.builder()
                    .userId((long)i)
                    .title("test " + i)
                    .content("text " + i)
                    .category(null)
                    .build();

            postRepository.save(post);
        }

        for (int i = 0; i<10; i++) {
            assertEquals(postRepository.findAllDesc().get(i).getId(), targetId--) ;
        }


    }
}