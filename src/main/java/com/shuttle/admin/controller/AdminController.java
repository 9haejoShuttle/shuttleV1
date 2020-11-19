package com.shuttle.admin.controller;

import javax.validation.Valid;

import com.shuttle.admin.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.shuttle.admin.dto.AdminSaveDto;
import com.shuttle.admin.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminController {
	private final AdminService adminService;
	private final PostService postService;

	@PostMapping("/admin/signup")
	public String saveAdmin(@Valid AdminSaveDto adminSaveDto) {
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
