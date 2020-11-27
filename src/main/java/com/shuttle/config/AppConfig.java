package com.shuttle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AppConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        /*
         *    참고 https://java.ihoney.pe.kr/498
         * */
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
