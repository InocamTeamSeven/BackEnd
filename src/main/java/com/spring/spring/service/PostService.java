package com.spring.spring.service;

import com.spring.spring.dto.MsgResponseDto;
import com.spring.spring.dto.PostRequestDto;
import com.spring.spring.dto.PostResponseDto;
import com.spring.spring.entity.Post;
import com.spring.spring.entity.PostLike;
import com.spring.spring.exception.CustomException;
import com.spring.spring.repository.PostLikeRepository;
import com.spring.spring.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.spring.spring.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;

    // 게시글 작성
    public PostResponseDto createPost(PostRequestDto postRequestDto) {
        Post post = new Post(postRequestDto);
        Post savePost = postRepository.save(post);

        return new PostResponseDto(savePost);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll();

        List<PostResponseDto> postResponseDtoList = new ArrayList<>();

        for (Post post : postList) {
            postResponseDtoList.add(new PostResponseDto(post));
        }

        return postResponseDtoList;
    }

    // 게시글 선택 조회
    public PostResponseDto getPost(Long post_id) {
        // 게시글이 있는지
        Post post = postRepository.findById(post_id).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        return new PostResponseDto(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long post_id, PostRequestDto postRequestDto) {
        // 게시글이 있는지
        Post post = postRepository.findById(post_id).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 작성자명 일치 확인
        if (!postRequestDto.getUsername().equals(post.getUsername())) {
            throw new CustomException(NOT_MATCH_USERNAME);
        }

        // 비밀번호 일치 확인
        if (!postRequestDto.getPassword().equals(post.getPassword())) {
            throw new CustomException(NOT_MATCH_PASSWORD);
        }

        post.update(postRequestDto);

        return new PostResponseDto(post);
    }

    // 게시글 삭제
    public MsgResponseDto deletePost(Long post_id, PostRequestDto postRequestDto) {
        // 게시글이 있는지
        Post post = postRepository.findById(post_id).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        // 비밀번호 일치 확인
        if (!postRequestDto.getPassword().equals(post.getPassword())) {
            throw new CustomException(NOT_MATCH_PASSWORD);
        }

        postRepository.delete(post);

        return new MsgResponseDto("게시글을 삭제했습니다.", HttpStatus.OK.value());
    }

    // 게시글 좋아요 개수
    @Transactional
    public MsgResponseDto savePostLike(Long post_id) {
        // 게시글이 있는지
        Post post = postRepository.findById(post_id).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        PostLike postLike = new PostLike(post);
        postLikeRepository.save(postLike);
        return new MsgResponseDto("게시글 좋아요", HttpStatus.OK.value());
    }
}