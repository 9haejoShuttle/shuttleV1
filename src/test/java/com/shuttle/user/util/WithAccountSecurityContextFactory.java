package com.shuttle.user.util;

import com.shuttle.user.UserService;
import com.shuttle.user.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

@RequiredArgsConstructor
public class WithAccountSecurityContextFactory implements WithSecurityContextFactory<WithAccount> {

    private final UserService userService;

    @Override
    public SecurityContext createSecurityContext(WithAccount withAccount) {

        String phone = withAccount.value();

        UserSignupRequestDto userSignupRequestDto = UserSignupRequestDto.builder()
                .phone(phone)
                .password("12345678")
                .name("cocoboy")
                .build();

        userService.signup(userSignupRequestDto);

        UserDetails principal = userService.loadUserByUsername(phone);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                principal,
                principal.getPassword(),
                principal.getAuthorities()
        );
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);

        return context;
    }
}
