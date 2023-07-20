package com.spring.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.spring.entity.Post;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostListResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String image;
    private String username;
    private LocalDate date;

    // 게시글 작성
    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.image = post.getImage();
        this.username = post.getUsername();
        this.date = post.getDate();

    }
}