package com.shuttle.user;

import com.shuttle.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;
    private final SignupValidator signupValidator;

    @InitBinder("userSignupRequestDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signupValidator);
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/signup")
    public String signupForm(Model model) {
        model.addAttribute("userSignupRequestDto", new UserSignupRequestDto());
        return "signup";
    }

    @PostMapping("/signup")
    public String signupSubmit(@Valid UserSignupRequestDto userSignupRequestDto, Errors errors,
                               Model model, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            model.addAttribute("errors", errors);
            return "signup";
        }

        String newUserName = userService.signup(userSignupRequestDto);
        redirect.addFlashAttribute("message", newUserName+"님 가입을 축하합니다.");
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String mypageIndex(@CurrentUser User user, Model model) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "mypage/info";
    }


    @PostMapping("/password")
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

    @GetMapping("/mypage/password")
    @ResponseBody
    public ResponseEntity<String> passwordUpdateForm() {
        /* TODO
         *   1. 현재 로그인 중인지 체크
         *   2. 로그인되어 있는 정보를 화면으로 보낸다.
         *   3. 비밀번호 변경 폼을 화면으로 보낸다.
         * */
        return new ResponseEntity<String>("aasdf", HttpStatus.OK);
    }
}
