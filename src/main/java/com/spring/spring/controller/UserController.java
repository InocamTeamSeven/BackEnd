package com.spring.spring.controller;

import com.spring.spring.dto.LoginRequestDto;
import com.spring.spring.dto.MsgResponseDto;
import com.spring.spring.dto.SignupRequestDto;
import com.spring.spring.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserController {
    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<MsgResponseDto> signup(@RequestBody @Valid SignupRequestDto signupRequestDto) {
        userService.signup(signupRequestDto);
        return ResponseEntity.ok(new MsgResponseDto("회원가입 완료", HttpStatus.OK.value()));
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity<MsgResponseDto> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        userService.login(loginRequestDto, response);
        return ResponseEntity.ok(new MsgResponseDto("로그인 완료", HttpStatus.OK.value()));
    }
}