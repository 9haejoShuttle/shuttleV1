package com.shuttle.admin;

import javax.validation.Valid;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.shuttle.domain.Admin;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;

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
