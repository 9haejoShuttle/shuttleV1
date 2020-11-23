package com.shuttle.user;

import com.shuttle.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signup(UserSignupRequestDto userSignupRequestDto) {
        User user = User.builder()
                .phone(userSignupRequestDto.getPhone())
                .name(userSignupRequestDto.getName())
                .password(passwordEncoder.encode(userSignupRequestDto.getPassword()))    //TODO PasswordEncoder 적용
                .email(userSignupRequestDto.getEmail())
                .build();

        User newUser = userRepository.save(user);

        return newUser.getName();
    }
}
