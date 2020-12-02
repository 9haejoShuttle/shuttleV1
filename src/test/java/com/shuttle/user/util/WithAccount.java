package com.shuttle.user.util;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/*
*   테스트 시 시큐리티 처리가 된 임의의 회원이 있어야 테스트할 수 있다.
*   이 애노테이션에 속성으로 아이디(phone)를 지정하면 임의의 사용자를 만들 수 있다.
*
* */
@Retention(RetentionPolicy.RUNTIME)
@WithSecurityContext(factory = WithAccountSecurityContextFactory.class)
public @interface WithAccount {
    String value();
}
