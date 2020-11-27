package com.shuttle.user;

import com.shuttle.domain.Role;
import com.shuttle.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserSignupRequestDto {
    @NotBlank
    @Length(min = 8, max = 15)
    private String phone;

    @NotBlank
    @Length(min = 8, max = 50)
    private String password;

    @NotBlank
    @Length(min = 8, max = 50)
    private String passwordConfirm;

    @NotBlank
    private String name;
}
