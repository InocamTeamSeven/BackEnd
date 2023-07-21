package com.spring.spring.dto;

import lombok.*;

@Getter
@Setter
//@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PostRequestDto {
    private String title;
    private String contents;
}