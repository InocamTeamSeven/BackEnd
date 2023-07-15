package com.spring.spring.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostRequestDto {
    private String title;
    private String contents;
    //    private String image;
    private String username;
    private String password;
}