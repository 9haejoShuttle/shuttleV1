package com.shuttle.config;

import com.shuttle.domain.Role;
import com.shuttle.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final DataSource dataSource;
    private final CutomOauth2UserService customOauth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // http.csrf().disable();


        http.authorizeRequests()
                .mvcMatchers("/admin/login", "/admin/signup", "/", "/index", "/signup","/test").permitAll()
                .mvcMatchers("/sendToken", "/forgotPassword", "/tokenVerified", "/login").anonymous()//모두 접근 가능
                .mvcMatchers(HttpMethod.GET, "/api/post/**").permitAll() //게시물 조회는 누구나 접근 가능
                .mvcMatchers("/admin/**", "/api/category/**").hasRole(Role.ADMIN.name()) //ADMIN 이하 접근 제한
                .anyRequest().authenticated();

        http.formLogin()
                .loginPage("/login")
                    .permitAll(false);
//                .and()
//                    .oauth2Login()
//                        .loginPage("/login/oauth")
//                        .userInfoEndpoint()
//                            .userService(customOauth2UserService);


        http.logout()
                .logoutSuccessUrl("/");

        http.rememberMe()
                .userDetailsService(userService)
                .tokenRepository(tokenRepository());

    }

    @Bean
    public PersistentTokenRepository tokenRepository() {
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        jdbcTokenRepository.setDataSource(dataSource);
        return jdbcTokenRepository;
    }


    /*
    *   static resource는 시큐리티가 필터링하지 않는다는 설정
    * */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations())
                .antMatchers("/error"); // reference : https://www.javaer101.com/article/584628.html
    }
}
