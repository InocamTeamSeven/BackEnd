package com.spring.spring.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.spring.dto.MsgResponseDto;
import com.spring.spring.dto.PostListResponseDto;
import com.spring.spring.dto.PostRequestDto;
import com.spring.spring.dto.PostResponseDto;
import com.spring.spring.entity.Post;
import com.spring.spring.entity.PostLike;
import com.spring.spring.entity.User;
import com.spring.spring.exception.CustomException;
import com.spring.spring.repository.PostLikeRepository;
import com.spring.spring.repository.PostRepository;
import com.spring.spring.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.spring.spring.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class PostService {
    private final UserRepository userRepository;
    private final UserService userService;
    private final PostRepository postRepository;
    private final PostLikeRepository postLikeRepository;
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    // 게시글 작성
    public PostResponseDto createPost(MultipartFile multipartFile, PostRequestDto requestDto, User user) {
        // 이미지 s3 업로드 후에 image url 반환
        String image = null;
        if (multipartFile != null) {
            image = uploadImage(multipartFile);
        }

        Post post = new Post(requestDto, image, user);
        postRepository.save(post);
        return new PostResponseDto(post);
    }

    // 게시글 전체 조회
    @Transactional(readOnly = true)
    public List<PostListResponseDto> getPostList() {
        List<Post> postList = postRepository.findAll();

        List<PostListResponseDto> postResponseDtoList = new ArrayList<>();
        for (Post post : postList) {

            postResponseDtoList.add(new PostListResponseDto(post));

        }

        return postResponseDtoList;
    }

    // 게시글 선택 조회 - 회원
    public PostResponseDto getPost(Long post_id, User user) {
        // 게시글이 있는지
        Post post = postRepository.findById(post_id).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        return new PostResponseDto(post, checkPostLike(post.getId(), user));
    }

    // 게시글 선택 조회 - 비회원
    public PostResponseDto getPost(Long post_id) {
        // 게시글이 있는지
        Post post = postRepository.findById(post_id).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        return new PostResponseDto(post);
    }

    // 게시글 수정
    @Transactional
    public PostResponseDto updatePost(Long post_id, PostRequestDto postRequestDto, User user) {
        // 게시글이 있는지 & 사용자의 권한 확인
        Post post = userService.findByPostIdAndUser(post_id, user);

        post.update(postRequestDto);

        return new PostResponseDto(post, checkPostLike(post.getId(), user));
    }

    // 게시글 삭제
    public MsgResponseDto deletePost(Long post_id, User user) {
        // 게시글이 있는지 & 사용자의 권한 확인
        Post post = userService.findByPostIdAndUser(post_id, user);

        // 이미지 s3 삭제
        if (post.getImage() != null) {
            amazonS3.deleteObject(bucket, post.getImage().substring(58));
        }

        postRepository.delete(post);

        return new MsgResponseDto("게시글을 삭제했습니다.", HttpStatus.OK.value());
    }

    // 게시글 좋아요 유/무 (false 면 좋아요 취소, true 면 좋아요)
    @Transactional
    public boolean checkPostLike(Long post_id, User user) {
        return postLikeRepository.existsByPostIdAndUserId(post_id, user.getId());
    }

    // 게시글 좋아요 개수
    @Transactional
    public MsgResponseDto savePostLike(Long post_id, User user) {
        // 게시글이 있는지
        Post post = postRepository.findById(post_id).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );

        if (!checkPostLike(post_id, user)) {
            PostLike postLike = new PostLike(post, user);
            postLikeRepository.save(postLike);
            return new MsgResponseDto("게시글 좋아요", HttpStatus.OK.value());
        } else {
            postLikeRepository.deleteByPostIdAndUserId(post_id, user.getId());
            return new MsgResponseDto("게시글 좋아요 취소", HttpStatus.OK.value());
        }
    }

    public String uploadImage(MultipartFile multipartFile) {
        String fileName = createFileName(multipartFile.getOriginalFilename());

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try(InputStream inputStream = multipartFile.getInputStream()) {
            amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata)
                    .withCannedAcl(CannedAccessControlList.PublicRead));
        } catch(IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
        }

        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }


}