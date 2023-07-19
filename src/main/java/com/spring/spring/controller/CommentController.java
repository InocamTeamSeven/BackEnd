package com.spring.spring.controller;

import com.spring.spring.dto.CommentRequestDto;
import com.spring.spring.dto.MsgResponseDto;
import com.spring.spring.security.UserDetailsImpl;
import com.spring.spring.service.CommetSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class CommentController {
    private final CommetSerivce commetSerivce;

    @PostMapping("/{post_id}/comment")
    public ResponseEntity<MsgResponseDto> createComment(@PathVariable Long post_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)  {
        return ResponseEntity.ok(commetSerivce.createComment(post_id, commentRequestDto, userDetails.getUser()));
    }

    @PutMapping("/{post_id}/comment/{comment_id}")
    public ResponseEntity<MsgResponseDto> updateComment(@PathVariable Long post_id, @PathVariable Long comment_id, @RequestBody CommentRequestDto commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails)  {
        return ResponseEntity.ok(commetSerivce.updateComment(post_id,comment_id,  commentRequestDto, userDetails.getUser()));
    }

    @DeleteMapping("/{post_id}/comment/{comment_id}")
    public ResponseEntity<MsgResponseDto> deleteComment(@PathVariable Long post_id, @PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails)  {
        return ResponseEntity.ok(commetSerivce.deleteComment(post_id, comment_id, userDetails.getUser()));
    }


}
