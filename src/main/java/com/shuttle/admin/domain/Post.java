package com.shuttle.admin.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID", nullable = false)
    private Category category;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String content;

    @Column(nullable = false)
    private Long userId;

    @Builder
    public Post(Category category, String title, String content, Long userId) {
        this.category = category;
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
