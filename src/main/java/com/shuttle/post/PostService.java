package com.shuttle.post;

import java.util.List;

public interface PostService {
    public Long postSave(PostSaveRequestDto postRequestSaveDto);
    public PostResponseDto findByPost(Long id);

    Long updatePost(PostSaveRequestDto requestDto, Long id);

    void deletePost(Long id);

    List<PostResponseDto> allPost();
}
