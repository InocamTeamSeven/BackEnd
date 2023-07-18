package com.spring.spring.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class SignupRequestDto {
    @Pattern(regexp = "^[a-z0-9]{4,10}$", message = "최소 4자 이상, 10자 이하의 알파벳 소문자, 숫자 구성")
    private String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "최소 8자 이상, 15자 이하의 알파벳 대소문자, 숫자 구성")
    private String password;
}
