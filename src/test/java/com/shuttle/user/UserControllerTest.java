package com.shuttle.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.domain.User;
import com.shuttle.user.dto.CheckTokenRequestDto;
import com.shuttle.user.dto.PasswordUpdateRequestDto;
import com.shuttle.user.util.WithAccount;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

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
    private UserService userService;

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
    
    @DisplayName("로그인 테스트 - 비활성화(탈퇴)한 유저 로그인 처리")
    @Transactional
    @WithAccount("01009876543")
    @Test
    void test_login_failure() throws Exception {
        User user = userRepository.findByPhone("01009876543").orElseThrow();
        user.setEnable(false);

        mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", "01009876543")
                .param("password", "12345678"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());

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
                .content(objectMapper.writeValueAsString(passwordUpdateRequestDto))
                .with(csrf()))
                .andExpect(status().isOk());

        User passwordUpdatedUser = userRepository.findByPhone("010111122222").get();

        assertNotEquals(beforeUpdateUser.getPassword(), passwordUpdatedUser.getPassword());
    }

    @DisplayName("비밀번호 변경 - 비밀번호 불일치")
    @WithAccount("010111122222")
    @Test
    void test_passwordUpdate_failure() throws Exception {
        PasswordUpdateRequestDto passwordUpdateRequestDto = new PasswordUpdateRequestDto();
        passwordUpdateRequestDto.setPassword("12341234");
        passwordUpdateRequestDto.setPasswordConfirm("asdf1234");

        mockMvc.perform(put("/mypage/password")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(passwordUpdateRequestDto))
                .with(csrf()))
                .andExpect(status().isInternalServerError());
    }
    
    @DisplayName("회원탈퇴")
    @WithAccount("010111122222")
    @Test
    void test_disableUser_success() throws Exception {

        User targetUser = userRepository.findByPhone("010111122222").orElseThrow();

        assertTrue(targetUser.isEnable());

        mockMvc.perform(put("/mypage/account"))
                .andExpect(status().isOk());

        User user = userRepository.findByPhone("010111122222").orElseThrow();

        assertFalse(user.isEnable());
    }

    @DisplayName("비밀번호 분실 - 인증 토큰 발행 후 확인")
    @Test
    void test_forgotPassword() throws Exception {
        User testUser = userRepository.findByPhone(USER_PHONE).orElseThrow();

        assertNull(testUser.getForgotPasswordToken());

        mockMvc.perform(post("/sendToken")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(USER_PHONE))
                .andExpect(status().isOk());

        User receivedTokenUser  = userRepository.findByPhone(USER_PHONE).orElseThrow();

        assertNotNull(receivedTokenUser.getForgotPasswordToken());

        CheckTokenRequestDto checkTokenRequestDto = new CheckTokenRequestDto(USER_PHONE, receivedTokenUser.getForgotPasswordToken());

        mockMvc.perform(post("/forgotPassword")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(checkTokenRequestDto)))
                .andExpect(status().isOk());

        User checkTokenSuccessUser = userRepository.findByPhone(USER_PHONE).orElseThrow();

        assertTrue(checkTokenSuccessUser.isTokenVerified());

    }
}
