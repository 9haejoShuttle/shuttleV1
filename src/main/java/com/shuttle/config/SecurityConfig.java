package com.shuttle.config;

import com.shuttle.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .mvcMatchers("/admin/login", "/admin/signup",
                        "/", "/index", "/login", "/signup").permitAll() //모두 접근 가능
                .mvcMatchers(HttpMethod.GET, "/api/post/**").permitAll() //게시물 조회는 누구나 접근 가능
                .mvcMatchers("/admin/**", "/api/category/**").hasRole(Role.ADMIN.name()) //ADMIN 이하 접근 제한
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login").permitAll();

        http.logout()
                .logoutSuccessUrl("/");
    }

    /*
    *   static resource는 시큐리티가 필터링하지 않는다는 설정
    * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
}
