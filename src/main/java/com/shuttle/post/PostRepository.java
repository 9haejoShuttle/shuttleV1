package com.shuttle.post;

import com.shuttle.domain.Post;
import com.shuttle.post.dto.PostResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.id DESC")
    List<PostResponseDto> findAllDesc();

}
