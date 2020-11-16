package com.shuttle.admin.dto;

import com.shuttle.admin.domain.Category;
import com.shuttle.admin.domain.Post;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PostResponseDto {
    private Long id;
    private Category category;
    private String title;
    private String content;
    private Long userId;

    //엔티티를 DTO로 변환
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.category = post.getCategory();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.userId = post.getUserId();
    }
}
