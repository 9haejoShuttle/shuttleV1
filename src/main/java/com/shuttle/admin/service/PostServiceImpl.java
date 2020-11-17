package com.shuttle.admin.service;

import com.shuttle.admin.domain.Post;
import com.shuttle.admin.dto.PostResponseDto;
import com.shuttle.admin.dto.PostSaveRequestDto;
import com.shuttle.admin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Long postSave(PostSaveRequestDto postRequestSaveDto) {
        return postRepository.save(postRequestSaveDto.toEntity()).getId();
    }

    @Transactional(readOnly = true)
    @Override
    public PostResponseDto findByPost(Long id) {
        Post postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id+"번 게시물이 없습니다."));
        return new PostResponseDto(postEntity);
    }

    @Transactional
    @Override
    public Long updatePost(PostSaveRequestDto requestDto, Long id) {
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id : "+id));
        existingPost.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
