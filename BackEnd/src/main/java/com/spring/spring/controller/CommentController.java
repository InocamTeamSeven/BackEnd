package com.spring.spring.controller;

import com.spring.spring.dto.CommentRequestDto;
import com.spring.spring.dto.CommentResponseDto;
import com.spring.spring.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{post_id}/comment")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable("post_id") Long postId) {
        List<CommentResponseDto> responseDto = commentService.getComments(postId);
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/{post_id}//comment")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable("post_id") Long postId, @RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.createComment(postId, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/{post_id}//comment/{comment_id}")
    public ResponseEntity<CommentResponseDto>updateComment(@PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId, @RequestBody CommentRequestDto requestDto,@RequestParam String username,@RequestParam String password) {
        CommentResponseDto responseDto = commentService.updateComment(postId, commentId, requestDto, username, password);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/{post_id}//comment/{comment_id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("post_id") Long postId, @PathVariable("comment_id") Long commentId, @RequestParam String username, @RequestParam String password) {
        commentService.deleteComment(postId, commentId, username, password);
        return ResponseEntity.noContent().build();
    }
}