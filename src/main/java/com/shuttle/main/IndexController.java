package com.shuttle.main;

import com.shuttle.domain.User;
import com.shuttle.user.CurrentUser;
import com.shuttle.user.SignupValidator;
import com.shuttle.user.UserService;
import com.shuttle.user.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class IndexController {
    private final UserService userService;
    private final SignupValidator signupValidator;

    @InitBinder("userSignupRequestDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(signupValidator);
    }

    @GetMapping("/")
    public String index(@CurrentUser User user, Model model){
        System.out.println("user : " + user);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "index";
    }



}
