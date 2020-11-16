package com.shuttle.admin.service;

import com.shuttle.admin.domain.Post;
import com.shuttle.admin.dto.PostResponseDto;
import com.shuttle.admin.dto.PostSaveRequestDto;
import com.shuttle.admin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Long postSave(PostSaveRequestDto postRequestSaveDto) {
        return postRepository.save(postRequestSaveDto.toEntity()).getId();
    }

    @Override
    public PostResponseDto findByPost(Long id) {
        Post postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id+"번 게시물이 없습니다."));
        return new PostResponseDto(postEntity);
    }
}
