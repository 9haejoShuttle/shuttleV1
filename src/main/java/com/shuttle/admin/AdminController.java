package com.shuttle.admin;

import javax.validation.Valid;

import com.shuttle.post.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.RequiredArgsConstructor;

@Slf4j
@RequiredArgsConstructor
@Controller
public class AdminController {
	private final AdminService adminService;
	private final PostService postService;
	private final AdminSaveDtoValidator adminSaveDtoValidator;

	/*
	* 	'adminSaveDto'를 바인딩할 때 만들어둔 Validator를 추가한다.
	* */
	@InitBinder("adminSaveDto")
	public void initBinder(WebDataBinder webDataBinder) {
		webDataBinder.addValidators(adminSaveDtoValidator);
	}

	@PostMapping("/admin/signup")
	public String saveAdmin(@Valid AdminSaveDto adminSaveDto, Errors errors, Model model) {
		if (errors.hasErrors()) {
			model.addAttribute("error", errors);
			return "admin/signup";
		}
		adminService.save(adminSaveDto);

		return "redirect:/";
	}

	@GetMapping("admin/login")
	public String login() {
		return "/admin/login";
	}

	@GetMapping("admin/post")
	public String allPost(Model model) {
		model.addAttribute("posts", postService.allPost());

		return "/admin/post/index";
	}
	
}
