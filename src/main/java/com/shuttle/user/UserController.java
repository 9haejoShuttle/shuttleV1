package com.shuttle.user;

import com.shuttle.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/mypage/{phone}/password")
    public void passwordUpdateForm() {
        /* TODO
        *   1. 현재 로그인 중인지 체크
        *   2. 로그인되어 있는 정보를 화면으로 보낸다.
        *   3. 비밀번호 변경 폼을 화면으로 보낸다.
        * */
    }

    @PostMapping("/mypage/{phone}/password")
    public void passwordUpdateSubmit(@CurrentUser User user) {
        /* TODO
         *   1. 어떤 계정으로 로그인되어 있는지 확인
         *   2. 비밀번호 변경 요청 폼을 받아서 Validator로 검사
         *   3. 통과하지 못하면 다시 변경 폼으로 리턴
         *   4. 통과한다면 비밀번호 변경작업
         * */
        System.out.println(user);
        System.out.println(user.getPhone());
        System.out.println(user.getName());
    }
}
