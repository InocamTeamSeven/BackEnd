package com.spring.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.spring.entity.Post;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostListResponseDto {
    private Long id;
    private String title;
    private String contents;
    private String image;

    // 게시글 작성
    public PostListResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.image = post.getImage();
    }
}