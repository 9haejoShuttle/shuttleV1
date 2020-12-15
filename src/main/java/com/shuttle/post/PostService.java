package com.shuttle.post;

import com.shuttle.post.dto.PostResponseDto;
import com.shuttle.post.dto.PostSaveRequestDto;

import java.util.List;

public interface PostService {

    public Long postSave(PostSaveRequestDto requestDto);
    public PostResponseDto findByPost(Long id);

    Long updatePost(PostSaveRequestDto requestDto, Long id);

    void deletePost(Long id);

    List<PostResponseDto> allPost();
}
