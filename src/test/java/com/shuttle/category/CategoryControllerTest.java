package com.shuttle.category;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.domain.Category;
import com.shuttle.category.CategoryDto;
import com.shuttle.category.CategoryRepository;
import com.shuttle.category.CategoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@AutoConfigureMockMvc
@SpringBootTest
class CategoryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryRepository categoryRepository;
    @AfterEach
    void deleteAll() { //하나의 테스트가 끝낼 때마다 DB 데이터 초기화
        categoryRepository.deleteAll();
    }
    @DisplayName("카테고리 등록")
    @WithMockUser(roles = "ADMIN")
    @Test
    void test_addCategory() throws Exception {
        //등록을 요청할 RequestDto
        CategoryDto requestDto = CategoryDto.builder()
                .categoryName("자유 게시판")
                .memo("로그인한 유저만 작성 가능")
                .build();
        /*
         *   가상 환경에서 post요청으로 등록을 처리하는 api를 호출
         *   ObjectMapper의 writeValueAsString()메서드를 이용해서
         *   request 데이터를 JSON으로 전송한다.(@RestController이기 때문에 JSON타입으로 요청해야 함)
         * */
        mockMvc.perform(post("/api/category")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
        //등록된 데이터를 가져온다.
        Category addedCategory = categoryRepository.findAll().get(0);
        String categoryName = addedCategory.getCategoryName();
        String categoryMemo = addedCategory.getMemo();
        //등록된 데이터와 요청한 데이터가 일치하는지 테스트
        assertEquals(categoryName, requestDto.getCategoryName());
        assertEquals(categoryMemo, requestDto.getMemo());
    }
    @DisplayName("카테고리 수정")
    @WithMockUser(roles = "ADMIN")
    @Test
    void test_update_category() throws Exception {
        //수정 요청 데이터
        CategoryDto updateRequestDto = CategoryDto.builder()
                .categoryName("공지사항")
                .memo("자유 게시판에서 공지사항으로 이름 수정")
                .build();
        //수정할 카테고리
        Category updateTargetCategory = addCategory();
        /*
         *   MockMvc를 이용해 수정 api호출해서 ObjectMapper의 writeValueAsString()메서드를 이용해서
         *   요청 데이터를 JSON타입으로 바꿔서 전송한다.
         * */
        mockMvc.perform(put("/api/category/"+updateTargetCategory.getId())
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(new ObjectMapper().writeValueAsString(updateRequestDto)))
                .andExpect(status().isOk());
        //수정된 카테고리를 불러온다.
        Category updatedCategory = categoryRepository.findById(updateTargetCategory.getId()).get();
        //수정된 카테고리와 수정 요청 데이터가 일치하는지 테스트
        assertThat(updatedCategory.getCategoryName())
                .isEqualTo(updateRequestDto.getCategoryName());
        assertThat(updatedCategory.getMemo())
                .isEqualTo(updateRequestDto.getMemo());
    }
    @DisplayName("카테고리 삭제")
    @WithMockUser(roles = "ADMIN")
    @Test
    void test_delete_category() throws Exception {
        //삭제할 카테고리를 불러온다.
        Category newCategory = addCategory();
        //존재하는지 확인
        assertThat(categoryRepository.findById(newCategory.getId())).isNotEmpty();
        //삭제 api호출
        mockMvc.perform(delete("/api/category/"+newCategory.getId())
                .with(csrf()))
                .andExpect(status().isOk());
        //삭제 되었는지 확인
        assertThat(categoryRepository.findById(newCategory.getId())).isEmpty();
    }
    /*
     *   모든 테스트에는 카테고리가 존재해야하므로 메서드로 분리
     *   생성한 카테고리 인스턴스를 반환한다.
     * */
    Category addCategory() {
        CategoryDto dto = CategoryDto.builder()
                .categoryName("자유 게시판")
                .memo("로그인한 유저만 작성 가능")
                .build();
        Category newCategory = categoryRepository.save(dto.toEntity());
        return newCategory;
    }
}