package com.shuttle.admin.repository;

import com.shuttle.admin.domain.Admin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;

    @DisplayName("관리자 아이디 등록")
    @Test
    void test_add_admin() {
        String newAdminName = "admin";
        Admin admin = Admin.builder()
        .name("admin")
        .password("12345678")
        .build();

        Admin newAdmin = adminRepository.save(admin);

        System.out.println(newAdmin);
        
        Assertions.assertEquals(newAdmin.getName(), newAdminName);
    }
}
