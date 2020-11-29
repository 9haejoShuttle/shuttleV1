package com.shuttle.user.dto;

import com.shuttle.domain.Role;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.util.List;

import static com.shuttle.domain.Role.USER;

/*
 *  스프링 시큐리티가 다루는 User정보와 우리가 다루는 User정보
 *   그 사이의 갭을 매꾸는 클래스
 * */


/*
*   TODO
*    SessionUser와 합쳐서 하나로 사용할 수 있는 방법 생각
*    OAuth2 로그인 사용자는 비밀번호가 없어서 여기서는 사용할 수 없음.
* */
@Getter
public class UserAccount extends User implements Serializable {
    private com.shuttle.domain.User user;

    /*
    *   스프링 시큐리티가 가진 userdetails.User와
    *   우리가 가진 domain.User를 매칭한다.
    * */
    public UserAccount(com.shuttle.domain.User user) {
        super(user.getPhone(), "asdf1234", List.of(new SimpleGrantedAuthority(user.getRoleKey())));
        this.user = user;
    }
}
