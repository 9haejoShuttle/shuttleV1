package com.shuttle.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @DisplayName("회원가입 테스트")
    @Test
    void test_signupSubmit() throws Exception {
        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                .phone("010-0000-0000")
                .password("12345678")
                .name("cocoboy")
                .build();

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userSignupRequestDto))
                .with(csrf()))
                .andExpect(status().isOk());

        User newUser = userRepository.findByName("cocoboy");

        assertEquals(userSignupRequestDto.getPhone(), newUser.getPhone());
        assertEquals(userSignupRequestDto.getName(), newUser.getName());
        //패스워드 인코딩이 되었다면 요청한 비밀번호와 달라야 함.
        assertNotEquals(userSignupRequestDto.getPassword(), newUser.getPassword());
    }

}