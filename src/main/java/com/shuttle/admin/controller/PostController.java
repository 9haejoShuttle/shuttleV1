package com.shuttle.admin.controller;

import com.shuttle.admin.domain.Post;
import com.shuttle.admin.dto.PostResponseDto;
import com.shuttle.admin.dto.PostSaveRequestDto;
import com.shuttle.admin.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostController {
    private final PostService postService;

    @PostMapping("/api/post")
    public Long savePost(@RequestBody PostSaveRequestDto requestDto) {
        return postService.postSave(requestDto);
    }

    @GetMapping("/api/post/{id}")
    public PostResponseDto findByPost(@PathVariable Long id) {
        return postService.findByPost(id);
    }

    @PutMapping("/api/post/{id}")
    public Long updatePost(@RequestBody PostSaveRequestDto requestDto, @PathVariable Long id) {
        return postService.updatePost(requestDto, id);
    }

    @DeleteMapping("/api/post/{id}")
    public void deletePost(@PathVariable Long id) {
        postService.deletePost(id);
    }

}
