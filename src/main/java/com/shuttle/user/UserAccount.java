package com.shuttle.user;

import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/*
 *  스프링 시큐리티가 다루는 User정보와 우리가 다루는 User정보
 *   그 사이의 갭을 매꾸는 클래스
 * */

@Getter
public class UserAccount extends User {
    private com.shuttle.domain.User user;

    /*
    *   스프링 시큐리티가 가진 userdetails.User와
    *   우리가 가진 domain.User를 매칭한다.
    * */
    public UserAccount(com.shuttle.domain.User user) {
        super(user.getPhone(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRoleKey())));
        this.user = user;
    }
}
