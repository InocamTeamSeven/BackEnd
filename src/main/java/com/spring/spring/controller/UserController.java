package com.spring.spring.controller;

import com.spring.spring.dto.MsgResponseDto;
import com.spring.spring.dto.SignupRequestDto;
import com.spring.spring.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<MsgResponseDto> signup (@RequestBody @Valid SignupRequestDto requestDto) {
        return ResponseEntity.ok(userService.signup(requestDto));
    }
}
