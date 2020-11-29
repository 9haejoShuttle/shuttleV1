package com.shuttle.main;

import com.shuttle.domain.User;
import com.shuttle.user.CurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class IndexController {

    @GetMapping("/")
    public String index(@CurrentUser User user, Model model){
        System.out.println("user : " + user);
        if (user != null) {
            model.addAttribute("user", user);
        }
        return "index";
    }



}
