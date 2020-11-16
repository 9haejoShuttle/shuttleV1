package com.shuttle.admin.service;

import com.shuttle.admin.dto.PostResponseDto;
import com.shuttle.admin.dto.PostSaveRequestDto;

public interface PostService {
    public Long postSave(PostSaveRequestDto postRequestSaveDto);
    public PostResponseDto findByPost(Long id);
}
