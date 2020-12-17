package com.shuttle.post;

import com.shuttle.post.dto.PostResponseDto;
import com.shuttle.post.dto.PostSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class PostController {

    private final PostService postService;

    //게시물 목록 조회
    @GetMapping("/post")
    public String savePost(Model model){
        model.addAttribute("post", postService.allPost());
        return "post/list";
    }

    @GetMapping("/post/save")
    public String post(){
        return "post/post-save";
    }

    @PostMapping("/post")
    public String write(PostSaveRequestDto requestDto) {
        postService.postSave(requestDto);
        return "post/list";
    }

    //게시물 수정/삭제
    @GetMapping("/post/update/{id}")
    public String postUpdate(@PathVariable Long id, Model model) {

        PostResponseDto dto = postService.findByPost(id);
        model.addAttribute("post", dto);

        return "post/post-update";
    }

}