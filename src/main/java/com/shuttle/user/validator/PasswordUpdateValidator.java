package com.shuttle.user.validator;

import com.shuttle.user.dto.PasswordUpdateRequestDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PasswordUpdateValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(PasswordUpdateRequestDto.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasswordUpdateRequestDto passwordUpdateRequestDto = (PasswordUpdateRequestDto) target;
        if (equalsNotPassword(passwordUpdateRequestDto)) {
            errors.rejectValue("passwordConfirm", "wrong.value", "비밀번호가 다릅니다.");
        }
    }

    private boolean equalsNotPassword(PasswordUpdateRequestDto passwordUpdateRequestDto) {
        return !passwordUpdateRequestDto.getPassword().equals(passwordUpdateRequestDto.getPasswordConfirm());
    }
}
