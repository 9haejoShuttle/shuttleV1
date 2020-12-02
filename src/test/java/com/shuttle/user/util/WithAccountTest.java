package com.shuttle.user.util;

import com.shuttle.domain.User;
import com.shuttle.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class WithAccountTest {

    @Autowired
    private UserRepository userRepository;

    @WithAccount("01088888888")
    @Test
    void test_withAccount() {

        Optional<User> testUser = userRepository.findByPhone("01088888888");
        assertThat(testUser).isNotEmpty();
        assertThat(testUser.get().getPhone())
                .isEqualTo("01088888888");
        
    }
}
