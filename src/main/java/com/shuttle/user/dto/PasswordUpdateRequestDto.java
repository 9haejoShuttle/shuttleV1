package com.shuttle.user.dto;

import lombok.Data;

@Data
public class PasswordUpdateRequestDto {
    private String password;
    private String passwordConfirm;
}
