package com.shuttle.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shuttle.domain.User;
import com.shuttle.domain.UserDetail;
import com.shuttle.user.dto.CheckTokenRequestDto;
import com.shuttle.user.dto.PasswordUpdateRequestDto;
import com.shuttle.user.dto.UserSignupRequestDto;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

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
    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserRepository userRepository;
    @Autowired private UserDetailRepository userDetailRepository;
    @Autowired PasswordEncoder passwordEncoder;

    final static String PLAIN_TEXT_PASSWORD = "12345678";
    final static String USER_PHONE = "01012341234";
    final static String USER_NAME = "cocoboy";
    final static ResultMatcher INTERNAL_SERVER_ERROR = status().isInternalServerError();
    final static ResultMatcher HTTP_OK = status().isOk();
    final static ResultMatcher REDIRECTION = status().is3xxRedirection();

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
        UserSignupRequestDto userSignupRequestDto
                = getUserSignupRequestDto("01000000000", PLAIN_TEXT_PASSWORD, PLAIN_TEXT_PASSWORD, "bobo");

        signupRequest(userSignupRequestDto, HTTP_OK);

        User newUser = findByPhone("01000000000");

        assertEquals(newUser.getPhone(), userSignupRequestDto.getPhone());
        assertNotEquals(newUser.getPassword(), userSignupRequestDto.getPassword());
        assertEquals(newUser.getName(), userSignupRequestDto.getName());
    }

    @DisplayName("회원가입 테스트 - 잘못된 값 요청 시 Validator")
    @Test
    void test_signup_failure() throws Exception {
        UserSignupRequestDto userSignupWrongRequestDto
                = getUserSignupRequestDto("01000000000", PLAIN_TEXT_PASSWORD, "12345611", "bobo");

        signupRequest(userSignupWrongRequestDto, INTERNAL_SERVER_ERROR);
    }

    @DisplayName("로그인 테스트 - 성공")
    @Test
    void test_loginSubmit() throws Exception {
        loginRequest(USER_PHONE, PLAIN_TEXT_PASSWORD, "/", REDIRECTION)
                .andExpect(authenticated().withUsername(USER_PHONE));

    }

    @DisplayName("로그인 테스트 - 비활성화(탈퇴)한 유저 로그인 처리")
    @Transactional
    @WithAccount("01009876543")
    @Test
    void test_login_failure() throws Exception {
        User user = findByPhone("01009876543");
        user.setEnable(false);

        loginRequest(user.getPhone(), PLAIN_TEXT_PASSWORD, "/login?error", REDIRECTION)
                .andExpect(unauthenticated());
    }

    @DisplayName("로그인 테스트 - 실패 - 아이디 또는 비밀번호 틀릴 경우")
    @Test
    void test_loginfailure() throws Exception {
        loginRequest("wrongUsername", PLAIN_TEXT_PASSWORD, "/login?error", REDIRECTION)
                .andExpect(unauthenticated());
    }

    @DisplayName("로그인 이력 남기기")
    @Test
    void test_loginHistory() throws Exception {
        loginRequest(USER_PHONE, PLAIN_TEXT_PASSWORD, "/", REDIRECTION)
                .andExpect(authenticated().withUsername(USER_PHONE));

        User user = findByPhone(USER_PHONE);
        Optional<UserDetail> userDetail = userDetailRepository.findById(user.getId());

        //userid로 불러온 userDetail이 empty가 아니어야 한다.
        assertFalse(userDetail.isEmpty());
        //userDetail에 있는 로그인 시간이 현재 시간 이전이어야 한다.
        assertTrue(userDetail.get().getLoginDate().isBefore(LocalDateTime.now()));
    }
    
    @DisplayName("비밀번호 변경 - 성공")
    @WithAccount("010111122222")
    @Test
    void test_passwordUpdate_submit() throws Exception {
        PasswordUpdateRequestDto passwordUpdateRequestDto =
                getPasswordUpdateRequestDto("asdf1234", "asdf1234");

        User beforeUpdateUser = findByPhone("010111122222");

        passwordUpdateRequest(passwordUpdateRequestDto, HTTP_OK);

        User passwordUpdatedUser = findByPhone("010111122222");

        assertNotEquals(beforeUpdateUser.getPassword(), passwordUpdatedUser.getPassword());
    }

    @DisplayName("비밀번호 변경 - 비밀번호 불일치")
    @WithAccount("010111122222")
    @Test
    void test_passwordUpdate_failure() throws Exception {
        PasswordUpdateRequestDto passwordUpdateRequestDto =
                getPasswordUpdateRequestDto("12341234", "asdf1234");

        passwordUpdateRequest(passwordUpdateRequestDto, INTERNAL_SERVER_ERROR);
    }

    @DisplayName("비밀번호 분실 - 인증 토큰 발행 후 확인")
    @Test
    void test_forgotPassword() throws Exception {
        User testUser = findByPhone(USER_PHONE);

        assertNull(testUser.getForgotPasswordToken());

        CheckTokenRequestDto checkTokenRequestDto = new CheckTokenRequestDto();
        checkTokenRequestDto.setPhone(USER_PHONE);

        //send token 요청
        sendTokenAndCheckTokenRequest(checkTokenRequestDto, "/sendToken", HTTP_OK);

        User receivedTokenUser  = findByPhone(USER_PHONE);

        //토큰 컬럼에 토큰이 추가 되었는지 체크
        assertNotNull(receivedTokenUser.getForgotPasswordToken());

        checkTokenRequestDto.setToken(receivedTokenUser.getForgotPasswordToken());

        //토큰 체크 요청
        sendTokenAndCheckTokenRequest(checkTokenRequestDto, "/tokenVerified", HTTP_OK);

        User checkTokenSuccessUser = findByPhone(USER_PHONE);

        assertTrue(checkTokenSuccessUser.isTokenVerified());

    }

    @DisplayName("회원탈퇴")
    @WithAccount("010111122222")
    @Test
    void test_disableUser_success() throws Exception {
        User targetUser = findByPhone("010111122222");

        assertTrue(targetUser.isEnable());

        mockMvc.perform(put("/mypage/account")
                .with(csrf()))
                .andExpect(status().isOk());

        User user = findByPhone("010111122222");

        assertFalse(user.isEnable());
    }

    private ResultActions signupRequest(UserSignupRequestDto userSignupRequestDto, ResultMatcher status) throws Exception {
        return mockMvc.perform(post("/signup")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(userSignupRequestDto))
                .with(csrf()))
                .andExpect(status);
    }

    private ResultActions loginRequest(String phone, String password, String redirectUrl ,ResultMatcher status) throws Exception {
        return mockMvc.perform(post("/login")
                .with(csrf())
                .param("username", phone)
                .param("password", password))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectUrl));
    }

    private ResultActions passwordUpdateRequest(PasswordUpdateRequestDto passwordUpdateRequestDto,
                                                ResultMatcher status) throws Exception {
        return mockMvc.perform(put("/mypage/password")
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(passwordUpdateRequestDto))
                .with(csrf()))
                .andExpect(status);
    }

    private ResultActions sendTokenAndCheckTokenRequest(CheckTokenRequestDto checkTokenRequestDto, String requestUrl,
                                                        ResultMatcher status) throws Exception {
        return mockMvc.perform(post(requestUrl)
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(checkTokenRequestDto)))
                .andExpect(status);
    }

    private UserSignupRequestDto getUserSignupRequestDto(String phone, String password, String passwordConfirm, String name) {
        return new UserSignupRequestDto(phone, password, passwordConfirm, name);
    }

    private User findByPhone(String phone) {
        return userRepository.findByPhone(phone).orElseThrow();
    }

    private PasswordUpdateRequestDto getPasswordUpdateRequestDto(String password, String passwordConfirm) {
        return new PasswordUpdateRequestDto(password, passwordConfirm);
    }

}
