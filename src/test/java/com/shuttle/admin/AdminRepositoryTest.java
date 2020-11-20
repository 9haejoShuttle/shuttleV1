package com.shuttle.admin;

import com.shuttle.domain.Admin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AdminRepositoryTest {

    @Autowired
    AdminRepository adminRepository;

    @Test
    void test_existByName () {
        adminRepository.save(Admin.builder()
                        .name("admin")
                        .password("12345678")
                        .build());

        assertTrue(adminRepository.existsByName("admin"));
    }

}