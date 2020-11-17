package com.shuttle.admin.service;

import com.shuttle.admin.dto.PostResponseDto;
import com.shuttle.admin.dto.PostSaveRequestDto;
import org.springframework.transaction.annotation.Transactional;

public interface PostService {
    public Long postSave(PostSaveRequestDto postRequestSaveDto);
    public PostResponseDto findByPost(Long id);

    Long updatePost(PostSaveRequestDto requestDto, Long id);

    void deletePost(Long id);
}
