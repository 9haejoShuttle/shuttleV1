package com.shuttle.post.dto;

import com.shuttle.domain.Category;
import com.shuttle.domain.Post;
import lombok.Getter;
import lombok.Setter;
/*
 *     게시물 조회 요청에 응답하기 위한 Response DTO
 *     요청을 처리하는 PostRequestSaveDto와 거의 유사하지만,
 *     관심사가 다르기 때문에 분리하였음.
 * */
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