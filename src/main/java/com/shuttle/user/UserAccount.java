package com.shuttle.user;

import com.shuttle.domain.Role;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

@Getter
public class UserAccount extends User {
    private com.shuttle.domain.User user;

    public UserAccount(com.shuttle.domain.User user) {
        super(user.getPhone(), user.getPassword(), List.of(new SimpleGrantedAuthority(user.getRoleKey())));
        this.user = user;
    }
}
