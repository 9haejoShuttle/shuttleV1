package com.shuttle.admin.dto;

import com.shuttle.admin.domain.Category;
import com.shuttle.admin.domain.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class PostSaveRequestDto {
    private Long id;
    private Category category;
    private String title;
    private String content;
    private Long userId;

    public Post toEntity() {
        Post postEntity = Post.builder()
                .category(this.category)
                .title(this.title)
                .content(this.content)
                .userId(this.userId)
                .build();
        return postEntity;
    }


}
