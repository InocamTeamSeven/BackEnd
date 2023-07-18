package com.spring.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
//@Setter
//@NoArgsConstructor
@AllArgsConstructor
public class PostRequestDto {
    private String title;
    private String contents;
    //    private String image;
//    private String username;
}