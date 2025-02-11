package com.shuttle.user;

import com.shuttle.domain.Payment;
import com.shuttle.domain.User;
import com.shuttle.payment.PaymentService;
import com.shuttle.user.dto.CheckTokenRequestDto;
import com.shuttle.user.dto.PasswordUpdateRequestDto;
import com.shuttle.user.dto.UserSignupRequestDto;
import com.shuttle.user.validator.PasswordUpdateValidator;
import com.shuttle.user.validator.SignupValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final PaymentService paymentService;
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

    @GetMapping(URL_MYPAGE+"/payment")
    public String paymentForm(@CurrentUser User user, @PageableDefault(size = 10, page = 0, sort = "id", direction = Sort.Direction.DESC) Pageable pageable,
                            Model model) {
        /*
        *       select가 총 세 번 날아간다.
        *       1. SELECT p.* FROM PAYMENT p WHERE p.user_id = ?
        *       2. SELECT COUNT(payment_id) FROM PAYMENT WHERE p.user_id = ?
        *       3. SELECT u.* FROM USER u WHERE u.id = ?
        * */
        Page<Payment> paymentsWithUser =
                paymentService.findAllPaymentsAndUser(user, pageable);

        model.addAttribute("payments", paymentsWithUser);
        model.addAttribute("user", user);

        return "mypage/payment";
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

        return ResponseEntity.ok(token);
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
                    .body(errors.getFieldError().getDefaultMessage());
        }

        String newUserName = userService.signup(userSignupRequestDto);

        return ResponseEntity.ok(newUserName);
    }

    @GetMapping(URL_MYPAGE)
    public String mypageIndex(@CurrentUser User user, Model model) {
        /*
        *   TODO
        *    마이페이지에서 첫 번째로 띄워야 할 정보
        *       - 사용자 정보.
        *       - 내 예약 현황(apply) 혹은 이용 중인 노선
        * */

        model.addAttribute("user", user);

        return "/mypage/info";
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
