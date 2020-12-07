package com.shuttle.config;

import com.shuttle.domain.User;
import lombok.Getter;

import java.io.Serializable;

/*
*   TOOO
*   AccountUser와 합칠 수 있는 방법 생각...
* */
@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
    }

}
