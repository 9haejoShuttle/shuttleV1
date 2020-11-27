package com.shuttle.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.domain.Role;
import com.shuttle.domain.User;
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

    final static String PLAINT_TEXT_PASSWORD = "12345678";
    final static String USER_PHONE = "01012341234";
    final static String USER_NAME = "cocoboy";

    @BeforeEach
    void addUser() {
        User newUser = User.builder()
                .phone(USER_PHONE)
                .name(USER_NAME)
                .password(passwordEncoder.encode(PLAINT_TEXT_PASSWORD))
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
        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                .phone("01000000000")
                .password(PLAINT_TEXT_PASSWORD)
                .name(USER_NAME)
                .build();

        mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(objectMapper.writeValueAsString(userSignupRequestDto))
                .with(csrf()))
                .andExpect(status().isOk());

        User newUser = userRepository.findByPhone("01000000000").get();

        assertEquals(userSignupRequestDto.getPhone(), newUser.getPhone());
        assertEquals(userSignupRequestDto.getName(), newUser.getName());
        //패스워드 인코딩이 되었다면 요청한 비밀번호와 달라야 함.
        assertNotEquals(userSignupRequestDto.getPassword(), newUser.getPassword());
    }

    @DisplayName("로그인 테스트 - 성공")
    @Test
    void test_loginSubmit() throws Exception {
        mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", USER_PHONE)
                .param("password", PLAINT_TEXT_PASSWORD))
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
                .param("password", PLAINT_TEXT_PASSWORD))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @DisplayName("비밀번호 변경")
    @WithUserDetails(USER_PHONE)
    //@Test
    void test_passwordUpdate() throws Exception {
        mockMvc.perform(post("/mypage/"+USER_PHONE+"/password")
        .with(csrf()));
    }

}