package com.shuttle.user.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
public class UserController {
    @GetMapping({ "/","/index" })
    public void index(){
        log.info("GET index.......");
    }
}