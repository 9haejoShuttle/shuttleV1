package com.shuttle.admin.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.shuttle.admin.domain.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
/*
* 	관리자 계정 등록 요청을 처리하는 DTO.
* 	당장 VIEW에서 관리자 계정을 만드는 폼은 만들지 않을 것이지만
*	나중을 위해..
* */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AdminSaveDto {
	
	@NotBlank
	@Length(min = 4, max = 10)
    @Pattern(regexp ="^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
	private String name;
	
	@NotBlank
	@Length(min = 8, max = 50)
	private String password;
	
	public Admin toEntity() {
		Admin entity = Admin.builder()
				.name(this.name)
				.password(this.password)
				.build();
		return entity;
	}
}
