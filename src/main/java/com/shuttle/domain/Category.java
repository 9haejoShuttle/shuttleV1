package com.shuttle.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CATEGORY_ID")
    private Long id;

    @Column(nullable = false)
    String categoryName;

    String memo;

    //카테고리 등록 생성자
    @Builder
    public Category(String categoryName, String memo) {
        this.categoryName = categoryName;
        this.memo = memo;
    }

    public void update(String categoryName, String memo) {
        this.categoryName = categoryName;
        this.memo = memo;
    }

}
