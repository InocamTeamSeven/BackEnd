package com.spring.spring.controller;

import com.spring.spring.dto.MsgResponseDto;
import com.spring.spring.dto.PostListResponseDto;
import com.spring.spring.dto.PostRequestDto;
import com.spring.spring.dto.PostResponseDto;
import com.spring.spring.security.UserDetailsImpl;
import com.spring.spring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
//@CrossOrigin(origins = "*")
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestPart(value = "image", required = false) MultipartFile multipartFile, @RequestPart PostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.createPost(multipartFile, requestDto, userDetails.getUser()));
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<PostListResponseDto>> getPostList() {
        return ResponseEntity.ok(postService.getPostList());
    }

    // 게시글 선택 조회
    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails != null) {
            return ResponseEntity.ok(postService.getPost(post_id, userDetails.getUser()));      // 회원
        } else {
            return ResponseEntity.ok(postService.getPost(post_id));         // 비회원
        }
    }

    // 게시글 수정
    @PutMapping("/{post_id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long post_id, @RequestBody PostRequestDto postRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.updatePost(post_id, postRequestDto, userDetails.getUser()));
    }

    // 게시글 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<MsgResponseDto> deletePost(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.deletePost(post_id, userDetails.getUser()));
    }

    // 게시글 좋아요
    @PostMapping("/like/{post_id}")
    public ResponseEntity<MsgResponseDto> savePostLike(@PathVariable Long post_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(postService.savePostLike(post_id, userDetails.getUser()));
    }
}