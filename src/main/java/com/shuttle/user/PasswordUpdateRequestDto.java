package com.shuttle.user;

import lombok.Data;

@Data
public class PasswordUpdateRequestDto {
    private String password;
    private String passwordConfirm;
}
