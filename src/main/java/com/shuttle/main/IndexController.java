package com.shuttle.main;

import com.shuttle.config.SessionUser;
import com.shuttle.domain.User;
import com.shuttle.user.CurrentUser;
import com.shuttle.user.dto.UserAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(@CurrentUser User user, Model model){
        if (user != null) {
            model.addAttribute("user", user);
        }

        return "index";
    }



}
