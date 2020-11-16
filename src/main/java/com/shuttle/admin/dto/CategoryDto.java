package com.shuttle.admin.dto;

import com.shuttle.admin.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
