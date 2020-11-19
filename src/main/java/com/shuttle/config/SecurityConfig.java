package com.shuttle.config;

import com.shuttle.admin.domain.Role;
import com.shuttle.admin.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import javax.sql.DataSource;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final AdminService adminService;
    private final DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/admin/login", "/admin/signup",
                        "/", "/index",
                        "/login", "/signup").permitAll() //모두 접근 가능
                .mvcMatchers(HttpMethod.GET, "/api/post/**").permitAll() //게시물 조회는 누구나 접근 가능
                .mvcMatchers("/admin/**", "/api/category/**").hasRole(Role.ADMIN.name()) //ADMIN 이하 접근 제한
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/admin/login")
                .loginPage("/login");

        http.logout()
                .logoutSuccessUrl("/");
    }
}
