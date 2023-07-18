package com.spring.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.spring.entity.Post;
import com.spring.spring.entity.PostLike;
import lombok.Getter;

import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private String image;
    private int postLikeCnt;
    private boolean postLikeCheck;

    // 게시글 작성
    public PostResponseDto(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.image = post.getImage();
        this.username = post.getUsername();
        this.postLikeCnt = post.getPostLikeList().size();
    }

    // 게시글 전체/선택 조회, 수정
    public PostResponseDto(Post post, boolean postLikeCheck) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.contents = post.getContents();
        this.image = post.getImage();
        this.username = post.getUsername();
        this.postLikeCnt = post.getPostLikeList().size();
        this.postLikeCheck = postLikeCheck;
    }
}