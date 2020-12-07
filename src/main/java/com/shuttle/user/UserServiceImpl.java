package com.shuttle.user;

import com.shuttle.domain.User;
import com.shuttle.user.dto.CheckTokenRequestDto;
import com.shuttle.user.dto.PasswordUpdateRequestDto;
import com.shuttle.user.dto.UserAccount;
import com.shuttle.user.dto.UserSignupRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
import java.util.Objects;
import java.util.UUID;

@Log4j2
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

    @Override
    public String sendToken(String phone) {
        User targetUser = userRepository.findByPhone(phone)
                .orElseThrow(() -> new IllegalArgumentException(phone + " 존재하지 않는 사용자입니다."));

        String token = UUID.randomUUID().toString().substring(0, 5);

        log.info("token : {} ", token);

        targetUser.setForgotPasswordToken(token);

        return token;
    }

    @Override
    public boolean checkToken(CheckTokenRequestDto checkTokenRequestDto) {
        User targetUser = userRepository.findByPhone(checkTokenRequestDto.getPhone())
                .orElseThrow(() -> new IllegalArgumentException(checkTokenRequestDto.getPhone() + " 존재하지 않는 사용자입니다."));

        return userTokenEqualsInputToken(targetUser, checkTokenRequestDto.getToken().trim());
    }

    private boolean userTokenEqualsInputToken(User targetUser, String token) {
        String userToken = targetUser.getForgotPasswordToken().trim();
        boolean equalsToken = Objects.isNull(userToken) ?  false : token.equals(userToken);

        if (equalsToken) {
            targetUser.setTokenVerified(true);
            login(targetUser);
        }

        return equalsToken;
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
