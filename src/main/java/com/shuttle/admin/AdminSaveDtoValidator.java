package com.shuttle.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/*
*  참고
*  https://atoz-develop.tistory.com/entry/Spring-Validation-%EC%B6%94%EC%83%81%ED%99%94
* */
@RequiredArgsConstructor
@Component
public class AdminSaveDtoValidator implements Validator {
    private final AdminRepository adminRepository;

    /*
    *   이 Validator로 어떤 타입의 인스턴스를 검증할 것인지 명시
    * */
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(AdminSaveDto.class);
    }

    /*
    *   검증
    * */
    @Override
    public void validate(Object target, Errors errors) {
        AdminSaveDto adminSaveDto = (AdminSaveDto) target;
        if (adminRepository.existsByName(adminSaveDto.getName())) {
            errors.rejectValue("email", "invalid.name", new Object[]{adminSaveDto.getName()}, "이미 사용 중인 아이디입니다.");
        }
    }
}
