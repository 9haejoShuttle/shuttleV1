package com.shuttle.admin;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.shuttle.admin.AdminService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shuttle.admin.AdminSaveDto;

@SpringBootTest
public class AdminServiceTest {
	
	@Autowired
	private AdminService adminService;
	
	@DisplayName("관리자 등록")
	@Test
	void test_saveAdmin() {
		String newAdminName = "admin";
		AdminSaveDto adminSaveDto = AdminSaveDto.builder()
				.name(newAdminName)
				.password("12345678")
				.build();
		
		assertEquals(newAdminName, adminService.save(adminSaveDto));
		
		
		
	}
}
