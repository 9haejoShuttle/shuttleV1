package com.shuttle.admin.controller;

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

}
