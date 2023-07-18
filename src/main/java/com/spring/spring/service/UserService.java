package com.spring.spring.service;

import com.spring.spring.dto.LoginRequestDto;
import com.spring.spring.dto.SignupRequestDto;
import com.spring.spring.entity.Post;
import com.spring.spring.entity.User;
import com.spring.spring.entity.UserRoleEnum;
import com.spring.spring.exception.CustomException;
import com.spring.spring.jwt.JwtUtil;
import com.spring.spring.repository.PostRepository;
import com.spring.spring.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static com.spring.spring.exception.ErrorCode.*;

@Service
@Validated
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    // 회원 가입
    public void signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new CustomException(DUPLICATED_USERNAME);
        }

        // 사용자 ROLE 확인 (admin = true 일 경우 아래 코드 수행)
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
                throw new CustomException(NOT_MATCH_ADMIN_TOKEN);
            }

            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록 (admin = false 일 경우 아래 코드 수행)
        User user = new User(username, password, role);
        userRepository.save(user);
    }

    // 로그인
    public void login(LoginRequestDto loginRequestDto, HttpServletResponse response) {
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new CustomException(NOT_MATCH_INFORMATION)
        );

        // 비밀번호 일치 여부 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new CustomException(NOT_MATCH_INFORMATION);
        }

        // Header 에 key 값과 Token 담기
        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, JwtUtil.createToken(user.getUsername(), user.getRole()));
    }

    // 사용자의 권한 확인 - 게시글
    Post findByPostIdAndUser(Long post_id, User user) {
        Post post;

        // ADMIN
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            post = postRepository.findById(post_id).orElseThrow(
                    () -> new CustomException(NOT_FOUND_BOARD)
            );
        // USER
        } else {
            post = postRepository.findByIdAndUserId(post_id, user.getId()).orElseThrow (
                    () -> new CustomException(NOT_FOUND_BOARD_OR_AUTHORIZATION)
            );
        }

        return post;
    }
}