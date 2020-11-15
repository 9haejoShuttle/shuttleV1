package com.shuttle.admin.service;

import javax.validation.Valid;

import org.springframework.stereotype.Service;

import com.shuttle.admin.domain.Admin;
import com.shuttle.admin.dto.AdminSaveDto;
import com.shuttle.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
	private final AdminRepository adminRepository;

	@Override
	public String save(@Valid AdminSaveDto adminSaveDto) {
		Admin newAdmin = adminRepository.save(adminSaveDto.toEntity());
		return newAdmin.getName();
	}
	
	

	
}
