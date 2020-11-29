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
        /*
        *   TODO
        *    마이페이지에서 첫 번째로 띄워야 할 정보
        *       - 사용자 정보.
        *       - 내 예약 현황(apply) 혹은 이용 중인 노선
        * */

        return "mypage/info";
    }

    @GetMapping("/mypage/password")
    public String passwordUpdateForm(@CurrentUser User user) {
        return "mypage/password";
    }

    @PutMapping("/mypage/password")
    @ResponseBody
    public ResponseEntity passwordUpdateSubmit(@CurrentUser User user, @Valid @RequestBody PasswordUpdateRequestDto passwordUpdateRequestForm,
                                               Errors errors, RedirectAttributes redirect) {
        userService.updatePassword(user, passwordUpdateRequestForm);
        return new ResponseEntity(HttpStatus.OK);
    }
}
