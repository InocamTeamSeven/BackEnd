package com.spring.spring.controller;

import com.spring.spring.dto.MsgResponseDto;
import com.spring.spring.dto.PostRequestDto;
import com.spring.spring.dto.PostResponseDto;
import com.spring.spring.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    // 게시글 작성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestPart(value = "image", required = false) MultipartFile multipartFile, PostRequestDto requestDto) {
        return ResponseEntity.ok(postService.createPost(multipartFile, requestDto));
    }

    // 게시글 전체 조회
    @GetMapping
    public ResponseEntity<List<PostResponseDto>> getPostList() {
        return ResponseEntity.ok(postService.getPostList());
    }

    // 게시글 선택 조회
    @GetMapping("/{post_id}")
    public ResponseEntity<PostResponseDto> getPost(@PathVariable Long post_id) {
        return ResponseEntity.ok(postService.getPost(post_id));
    }

    // 게시글 수정
    @PutMapping("/{post_id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long post_id, @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.updatePost(post_id, postRequestDto));
    }

    // 게시글 삭제
    @DeleteMapping("/{post_id}")
    public ResponseEntity<MsgResponseDto> deletePost(@PathVariable Long post_id, @RequestBody PostRequestDto postRequestDto) {
        return ResponseEntity.ok(postService.deletePost(post_id, postRequestDto));
    }

    // 게시글 좋아요
    @PostMapping("/like/{post_id}")
    public ResponseEntity<MsgResponseDto> savePostLike(@PathVariable Long post_id) {
        return ResponseEntity.ok(postService.savePostLike(post_id));
    }
}