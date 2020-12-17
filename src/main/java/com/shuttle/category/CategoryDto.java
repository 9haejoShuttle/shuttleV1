package com.shuttle.category;

import com.shuttle.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
/*
 *   카테고리의 DTO는 따로 요청을 처리하는 DTO와 응답을 처리하는 DTO를 따로 분리하지 않았다.
 *   데이터도 두 개 뿐이고, 완전히 같기 때문에 하나의 DTO로 공유해서 사용해도 괜찮을 듯해서.
 * */
@Builder
@Getter @Setter
public class CategoryDto {
    private String categoryName;
    private String memo;

    public Category toEntity() {
        Category newCategory = Category.builder()
                .categoryName(this.categoryName)
                .memo(this.memo)
                .build();
        return newCategory;
    }
}
