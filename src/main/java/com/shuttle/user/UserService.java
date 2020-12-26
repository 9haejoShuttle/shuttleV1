package com.shuttle.user;

import com.shuttle.domain.User;
import com.shuttle.user.dto.CheckTokenRequestDto;
import com.shuttle.user.dto.PasswordUpdateRequestDto;
import com.shuttle.user.dto.UserSignupRequestDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    String signup(UserSignupRequestDto userSignupRequestDto);

    void updatePassword(User user, PasswordUpdateRequestDto passwordUpdateRequestDto);

    void disable(User user);

    String sendToken(String phoneNumber);

    boolean checkToken(CheckTokenRequestDto checkTokenRequestDto);

    User findUserWithPayments(User user);
}
