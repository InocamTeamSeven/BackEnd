package com.spring.spring.service;

import com.spring.spring.dto.MsgResponseDto;
import com.spring.spring.dto.SignupRequestDto;
import com.spring.spring.entity.User;
import com.spring.spring.entity.UserRoleEnum;
import com.spring.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public MsgResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());
        UserRoleEnum role = UserRoleEnum.USER;

        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 이름");
        }

        User user = new User(username, password, role);
        userRepository.save(user);

        return new MsgResponseDto("회원가입 성공", HttpStatus.OK.value());
    }
}
