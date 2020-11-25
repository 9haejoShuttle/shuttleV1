package com.shuttle.user;

import com.shuttle.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

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

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User loginUser = userRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException(phone + "은 존재하지 않는 사용자입니다."));

        return new UserAccount(loginUser);
    }
}
