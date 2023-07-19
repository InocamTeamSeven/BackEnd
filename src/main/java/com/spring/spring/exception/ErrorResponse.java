package com.spring.spring.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
    private final int statusCode;
    private final String message;
    public ErrorResponse(ErrorCode errorCode) {
        this.statusCode = errorCode.getHttpStatus().value();
        this.message = errorCode.getMessage();
    }
}