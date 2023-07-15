package com.spring.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.spring.entity.Post;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private Long id;
    private String title;
    private String contents;
    //    private String image;
    private int postLikeCnt;
    private String username;

    // 게시글 작성
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
//        this.image = post.getImage();
        this.username = post.getUsername();
        this.postLikeCnt = post.getPostLikeList().size();
    }
}