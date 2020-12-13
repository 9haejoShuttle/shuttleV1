package com.shuttle.post;

import com.shuttle.post.dto.PostResponseDto;
import com.shuttle.post.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
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

    @GetMapping("/post/save")
    public String list(Model model){
        model.addAttribute("post", postService.allPost());
        return "post-save";
    }

    @GetMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {
        PostResponseDto dto = postService.findByPost(id);
        model.addAttribute("post", dto);

        return "post-update";
    }
}
