package com.shuttle.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.domain.Role;
import com.shuttle.domain.User;
import com.shuttle.user.util.WithAccount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

    @Autowired
    PasswordEncoder passwordEncoder;

    final static String PLAIN_TEXT_PASSWORD = "12345678";
    final static String USER_PHONE = "01012341234";
    final static String USER_NAME = "cocoboy";

    @BeforeEach
    void addUser() {
        User newUser = User.builder()
                .phone(USER_PHONE)
                .name(USER_NAME)
                .password(passwordEncoder.encode(PLAIN_TEXT_PASSWORD))
                .build();

        userRepository.save(newUser);
    }

    @AfterEach
    void deleteUser() {
        userRepository.deleteAll();
    }

    @DisplayName("회원가입 테스트")
    @Test
    void test_signupSubmit() throws Exception {
        String phone = "01000000000";
        String name = "bobo";
        mockMvc.perform(post("/signup")
        .param("phone", phone)
        .param("password", PLAIN_TEXT_PASSWORD)
        .param("passwordConfirm", PLAIN_TEXT_PASSWORD)
        .param("name", name)
        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(flash().attributeExists("message"));

        User newUser = userRepository.findByPhone("01000000000").get();

        assertEquals(newUser.getPhone(), phone);
        assertNotEquals(newUser.getPassword(), PLAIN_TEXT_PASSWORD);
        assertEquals(newUser.getName(), name);
    }

    @DisplayName("회원가입 테스트 - 잘못된 값 요청 시 Validator")
    @Test
    void test_signup_failure() throws Exception {
        String phone = "01000000000";
        String name = "bobo";
        mockMvc.perform(post("/signup")
                .param("phone", phone)
                .param("password", PLAIN_TEXT_PASSWORD)
                .param("passwordConfirm", "12333333")
                .param("name", name)
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("errors"))
                .andExpect(view().name("signup"));
    }

    @DisplayName("로그인 테스트 - 성공")
    @Test
    void test_loginSubmit() throws Exception {
        mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", USER_PHONE)
                .param("password", PLAIN_TEXT_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername(USER_PHONE));

    }

    @DisplayName("로그인 테스트 - 실패 - 아이디 또는 비밀번호 틀릴 경우")
    @Test
    void test_loginfailure() throws Exception {
        mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", "wrongUsername")
                .param("password", PLAIN_TEXT_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }
    
    @DisplayName("비밀번호 변경 - 성공")
    @WithAccount("010111122222")
    @Test
    void test_passwordUpdate_submit() throws Exception {
        PasswordUpdateRequestDto passwordUpdateRequestDto = new PasswordUpdateRequestDto();
        passwordUpdateRequestDto.setPassword("asdf1234");
        passwordUpdateRequestDto.setPasswordConfirm("asdf1234");


        User beforeUpdateUser = userRepository.findByPhone("010111122222").get();

        mockMvc.perform(put("/mypage/password")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(passwordUpdateRequestDto)))
                .andExpect(status().isOk());


        User passwordUpdatedUser = userRepository.findByPhone("010111122222").get();

        assertNotEquals(beforeUpdateUser.getPassword(), passwordUpdatedUser.getPassword());
    }



}