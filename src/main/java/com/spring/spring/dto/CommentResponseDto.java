package com.spring.spring.dto;

import com.spring.spring.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private String username;
    private String contents;

    public CommentResponseDto(Comment comment) {
        this.username = comment.getUsername();
        this.contents = comment.getContents();
    }
}
