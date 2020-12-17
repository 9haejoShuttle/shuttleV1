package com.shuttle.post.dto;

import com.shuttle.domain.Category;
import com.shuttle.domain.Post;
import lombok.*;

import java.time.LocalDateTime;

/*
 *   VIEW에서 들어오는 게시물 등록/수정 요청을 처리하기 위한 DTO.
 *   이 DTO는 요청만 처리하고, 응답할 때는 응답 DTO(PostResponseDto)를 이용한다.
 *   두 dto가 유사하기 때문에 하나로 합칠 수도 있겠지만 서로 관심사가 다르기 때문에 분리했음.
 * */
@Getter @Setter
@ToString
@NoArgsConstructor
public class PostSaveRequestDto {
    private Category category;
    private String title;
    private String content;
    private Long userId;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;

    public Post toEntity() {

        Post postEntity = Post.builder()
                .category(this.category)
                .title(this.title)
                .content(this.content)
                .userId(this.userId)
                .build();
        return postEntity;
    }

    @Builder
    public PostSaveRequestDto(Category category, String title, Long userId, String content, LocalDateTime createdDate, LocalDateTime modifiedDate) {
        this.category = category;
        this.title = title;
        this.userId = userId;
        this.content = content;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
}