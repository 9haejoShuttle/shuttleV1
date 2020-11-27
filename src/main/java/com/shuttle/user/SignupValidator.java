package com.shuttle.user;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@RequiredArgsConstructor
@Component
public class SignupValidator implements Validator {

    private final UserRepository userRepository;

    //이 Validator로 어떤 타입의 인스턴스를 검증할 것인지
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(UserSignupRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        UserSignupRequestDto userSignupRequestDto = (UserSignupRequestDto) target;
        if (userRepository.existsByPhone(userSignupRequestDto.getPhone())) {
            errors.rejectValue("phone", "invalid.phone", new Object[]{userSignupRequestDto.getPhone()}, "이미 가입한 사용자입니다.");
        }
        if (!userSignupRequestDto.getPassword().equals(userSignupRequestDto.getPasswordConfirm())) {
            errors.rejectValue("passwordConfirm", "wrong.value", "비밀번호가 다릅니다.");
        }
    }
}
