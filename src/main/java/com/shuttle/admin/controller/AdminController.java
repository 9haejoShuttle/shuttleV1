package com.shuttle.admin.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import com.shuttle.admin.dto.AdminSaveDto;
import com.shuttle.admin.service.AdminService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class AdminController {
	private final AdminService adminService;
	
	@PostMapping("/admin/new")
	public String saveAdmin(@Valid AdminSaveDto adminSaveDto) {
		adminService.save(adminSaveDto);

		return "redirect:/admin/new";
	}
	
}
