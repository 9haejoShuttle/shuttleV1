package com.shuttle.user;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@RequestBody UserSignupRequestDto userSignupRequestDto) {
        //가입한 이름을 리턴해서 화면에 띄운다.
        String newUserName = userService.signup(userSignupRequestDto);
        return newUserName;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
