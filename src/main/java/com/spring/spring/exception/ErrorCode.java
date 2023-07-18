package com.spring.spring.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // 회원가입시 동일한 이름이 존재하는 경우
    DUPLICATE_USERNAME(HttpStatus.BAD_REQUEST, "동일한 이름이 존재합니다."),

    // 작성자명이 일치하지 않을 경우
    NOT_MATCH_USERNAME(HttpStatus.BAD_REQUEST, "작성자가 아닙니다."),

    // 비밀번호가 일치하지 않을 경우
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    // DB 에 해당 게시글/댓글/대댓글이 존재하지 않는 경우
    NOT_FOUND_BOARD(HttpStatus.BAD_REQUEST, "게시글을 찾을 수 없습니다."),
    NOT_FOUND_COMMENT(HttpStatus.BAD_REQUEST, "댓글을 찾을 수 없습니다."),
    NOT_FOUND_REPLY(HttpStatus.BAD_REQUEST, "대댓글을 찾을 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}