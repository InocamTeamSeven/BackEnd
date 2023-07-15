package com.spring.spring.controller;

import com.spring.spring.dto.CommentRequestDto;
import com.spring.spring.dto.CommentResponseDto;
import com.spring.spring.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/comment")
    public ResponseEntity<List<CommentResponseDto>> getComments() {
        List<CommentResponseDto> responseDto = commentService.getComments();
        return ResponseEntity.ok(responseDto);
    }


    @PostMapping("/comment")
    public ResponseEntity<CommentResponseDto> createComment(@RequestBody CommentRequestDto requestDto) {
        CommentResponseDto responseDto = commentService.createComment(requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping("/comment/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto, @RequestParam String username, @RequestParam String password) {
        CommentResponseDto responseDto = commentService.updateComment(id, requestDto, username, password);
        return ResponseEntity.ok(responseDto);
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @RequestParam String username, @RequestParam String password) {
        commentService.deleteComment(id, username, password);
        return ResponseEntity.noContent().build();
    }
}