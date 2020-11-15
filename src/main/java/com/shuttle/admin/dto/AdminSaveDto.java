package com.shuttle.admin.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import com.shuttle.admin.domain.Admin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
