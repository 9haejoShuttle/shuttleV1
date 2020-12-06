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

    final static String URL_MYPAGE = "/mypage";
    final static String URL_PASSWORD = "/password";
    final static String URL_ACCOUNT = "/account";
    final static String URL_SIGNUP = "/signup";

    final static ResponseEntity<HttpStatus> SUCCESS = ResponseEntity.ok().build();
    final static ResponseEntity<HttpStatus> FAIL = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

    @InitBinder("userSignupRequestDto")
    public void signupValidatorBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signupValidator);
    }

    @InitBinder("passwordUpdateRequestDto")
    public void passwordUpdateBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(passwordUpdateValidator);
    }

    @GetMapping("/login")
    public void login() {
    }

    @GetMapping("/forgotPassword")
    public void findPasswordForm() {

    }

    @PostMapping("/sendToken")
    @ResponseBody
    public ResponseEntity sendToken(@RequestBody CheckTokenRequestDto checkTokenRequestDto) {
        String token = userService.sendToken(checkTokenRequestDto.getPhone());

        return SUCCESS;
    }

    @PostMapping("/tokenVerified")
    @ResponseBody
    public ResponseEntity tokenVerified(@RequestBody CheckTokenRequestDto checkTokenRequestDto) {
        boolean result = userService.checkToken(checkTokenRequestDto);

        return result ? SUCCESS : FAIL;
    }

    @GetMapping(URL_SIGNUP)
    public void signupForm() {
    }

    @PostMapping(URL_SIGNUP)
    @ResponseBody
    public ResponseEntity signupSubmit(@RequestBody @Valid UserSignupRequestDto userSignupRequestDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(errors.getFieldError());
        }

        String newUserName = userService.signup(userSignupRequestDto);

        return ResponseEntity.ok(newUserName);
    }

    @GetMapping(URL_MYPAGE)
    public ResponseEntity mypageIndex(@CurrentUser User user, Model model) {
        /*
        *   TODO
        *    마이페이지에서 첫 번째로 띄워야 할 정보
        *       - 사용자 정보.
        *       - 내 예약 현황(apply) 혹은 이용 중인 노선
        * */
        return ResponseEntity.ok(user);
    }

    @GetMapping(URL_MYPAGE+URL_PASSWORD)
    public void passwordUpdateForm(@CurrentUser User user) {
    }

    @PutMapping(URL_MYPAGE + URL_PASSWORD)
    @ResponseBody
    public ResponseEntity passwordUpdateSubmit(@CurrentUser User user, @Valid @RequestBody PasswordUpdateRequestDto passwordUpdateRequestDto,
                                               Errors errors) {
        if (errors.hasErrors()) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        userService.updatePassword(user, passwordUpdateRequestDto);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping(URL_MYPAGE + URL_ACCOUNT)
    public void disableUserForm(@CurrentUser User user) {
    }

    @PutMapping(URL_MYPAGE + URL_ACCOUNT)
    @ResponseBody
    public ResponseEntity disableUserSubmit(@CurrentUser User user) {
        userService.disable(user);
        return SUCCESS;
    }

}
