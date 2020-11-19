package com.shuttle.admin.service;

import com.shuttle.admin.domain.Post;
import com.shuttle.admin.dto.PostResponseDto;
import com.shuttle.admin.dto.PostSaveRequestDto;
import com.shuttle.admin.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public Long postSave(PostSaveRequestDto postRequestSaveDto) {
        //게시물 등록 후 id반환
        return postRepository.save(postRequestSaveDto.toEntity()).getId();
    }

    @Transactional(readOnly = true) //쓰는 작업이 필요없을 때 readOnly속성을 주면 성능 향상
    @Override
    public PostResponseDto findByPost(Long id) {
        //입력 받은 id값을 가진 게시물이 있다면 가져오고 없다면 예외 날림.
        Post postEntity = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(id+"번 게시물이 없습니다."));
        return new PostResponseDto(postEntity);
    }

    @Transactional
    @Override
    public Long updatePost(PostSaveRequestDto requestDto, Long id) {
        //입력 받은 id값을 가진 게시물이 있다면 가져오고 없다면 예외 날림.
        Post existingPost = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시물이 없습니다. id : "+id));
        //업데이트 요청 받은 값으로 엔티티의 상태를 변경한다.
        existingPost.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public List<PostResponseDto> allPost() {
        return postRepository.findAllDesc();
    }
}
