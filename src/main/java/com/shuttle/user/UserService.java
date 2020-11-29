package com.shuttle.user;

import com.shuttle.domain.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String signup(UserSignupRequestDto userSignupRequestDto);

    void updatePassword(User user, PasswordUpdateRequestDto passwordUpdateRequestDto);
}
