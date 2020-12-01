package com.shuttle.user;

import com.shuttle.domain.User;
import com.shuttle.user.dto.CheckTokenRequestDto;
import com.shuttle.user.dto.PasswordUpdateRequestDto;
import com.shuttle.user.dto.UserSignupRequestDto;
import com.shuttle.user.validator.PasswordUpdateValidator;
import com.shuttle.user.validator.SignupValidator;
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
    private final PasswordUpdateValidator passwordUpdateValidator;

    @InitBinder("userSignupRequestDto")
    public void signupValidatorBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signupValidator);
    }

    @InitBinder("passwordUpdateRequestDto")
    public void passwordUpdateBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(passwordUpdateValidator);
    }


    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/forgotPassword")
    public void findPasswordForm() {

    }

    @PostMapping("/sendToken")
    @ResponseBody
    public ResponseEntity sendToken(@RequestBody String phone) {
        String token = userService.sendToken(phone);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/forgotPassword")
    @ResponseBody
    public ResponseEntity findPasswordSubmit(@RequestBody CheckTokenRequestDto checkTokenRequestDto) {
        if (userService.checkToken(checkTokenRequestDto))
            userService.loadUserByUsername(checkTokenRequestDto.getPhone());

        return new ResponseEntity(HttpStatus.OK);
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
        model.addAttribute("user", user);
        return "mypage/info";
    }

    @GetMapping("/mypage/password")
    public String passwordUpdateForm(@CurrentUser User user) {
        return "mypage/password";
    }

    @PutMapping("/mypage/password")
    @ResponseBody
    public ResponseEntity passwordUpdateSubmit(@CurrentUser User user, @Valid @RequestBody PasswordUpdateRequestDto passwordUpdateRequestDto,
                                               Errors errors, RedirectAttributes redirect) {
        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userService.updatePassword(user, passwordUpdateRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/mypage/account")
    public String disableUserForm(@CurrentUser User user) {
        return "mypage/delete";
    }

    @PutMapping("/mypage/account")
    @ResponseBody
    public ResponseEntity disableUserSubmit(@CurrentUser User user) {
        userService.disable(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}
