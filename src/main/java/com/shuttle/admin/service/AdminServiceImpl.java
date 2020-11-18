package com.shuttle.admin.service;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shuttle.admin.domain.Admin;
import com.shuttle.admin.dto.AdminSaveDto;
import com.shuttle.admin.repository.AdminRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {
	private final AdminRepository adminRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public String save(@Valid AdminSaveDto adminSaveDto) {
		Admin addAdmin = Admin.builder()
				.name(adminSaveDto.getName())
				.password(passwordEncoder.encode(adminSaveDto.getPassword()))
				.build();

		Admin newAdmin = adminRepository.save(addAdmin);


		return newAdmin.getName();
	}
	
}
