package com.shuttle.user;

import com.shuttle.domain.User;
import com.shuttle.user.dto.PasswordUpdateRequestDto;
import com.shuttle.user.dto.UserAccount;
import com.shuttle.user.dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
                .build();

        User newUser = userRepository.save(user);

        login(newUser);

        return newUser.getName();
    }

    @Override
    public void updatePassword(User user, PasswordUpdateRequestDto passwordUpdateRequestDto) {
        User targetUser = userRepository.findById(user.getId()).get();
        targetUser.updatePassword(passwordEncoder.encode(passwordUpdateRequestDto.getPassword()));
    }

    @Override
    public void disable(User user) {
        User targetUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new IllegalArgumentException(user.getName() + "존재하지 않는 유저입니다."));

        targetUser.setEnable(false);
    }

    public void login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                new UserAccount(user),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(user.getRoleKey()))
        );
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(token);
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        User loginUser = userRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException(phone + "은 존재하지 않는 사용자입니다."));

        if (disableUser(loginUser)) {
            throw new IllegalArgumentException(loginUser.getPhone() + "은 탈퇴한 유저입니다.");
        }

        return new UserAccount(loginUser);
    }

    private boolean disableUser(User loginUser) {
        return !loginUser.isEnable();
    }
}
