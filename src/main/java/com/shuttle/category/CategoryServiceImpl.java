//package com.shuttle.category;
//
//import com.shuttle.domain.Category;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Transactional  //JPA 엔티티는 트랜잭션 안에서만 영속가 된다.
//@RequiredArgsConstructor
//@Service
//public class CategoryServiceImpl implements CategoryService {
//    private final CategoryRepository categoryRepository;
//
//    @Override
//    public Long saveCategory(CategoryDto categorySaveRequestDto) {
//        //카테고리를 등록하고, id값을 반환한다.
//        return categoryRepository.save(categorySaveRequestDto.toEntity()).getId();
//    }
//
//    @Override
//    public void deleteCategory(Long id) {
//        categoryRepository.deleteById(id);
//    }
//
//    @Transactional(readOnly = true)
//    @Override
//    public List<Category> getCategories() {
//        List<Category> categories = categoryRepository.findAll();
//        return categories;
//    }
//
//    @Override
//    public void updateCategory(CategoryDto categoryDto, Long id) {
//        //입력 받은 id값을 가진 카테고리를 조회하고, 없다면 예외를 날리고 있다면 Category타입으로 저장한다.
//        Category updateTargetCatgory = categoryRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException(id+"번 카테고리가 존재하지 않습니다."));
//
//        //업데이트 요청 받은 값으로 엔티티의 상태를 변경한다.
//        updateTargetCatgory.update(categoryDto.getCategoryName(), categoryDto.getMemo());
//    }
//}
