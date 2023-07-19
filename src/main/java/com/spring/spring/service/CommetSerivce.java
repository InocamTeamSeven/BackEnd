package com.spring.spring.service;

import com.spring.spring.dto.CommentRequestDto;
import com.spring.spring.dto.MsgResponseDto;
import com.spring.spring.dto.PostResponseDto;
import com.spring.spring.entity.Comment;
import com.spring.spring.entity.Post;
import com.spring.spring.entity.User;
import com.spring.spring.entity.UserRoleEnum;
import com.spring.spring.exception.CustomException;
import com.spring.spring.repository.CommentRepository;
import com.spring.spring.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spring.spring.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class CommetSerivce {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    public MsgResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, User user) {
        Post post  = checkPost(postId);

        Comment comment = new Comment(commentRequestDto, user, post);
        commentRepository.save(comment);
        return new MsgResponseDto("댓글 작성 성공", HttpStatus.OK.value());
    }

    @Transactional
    public MsgResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto, User user) {
        checkPost(postId);

        Comment comment = findByCommentIdAndUser(commentId,user);
        comment.update(commentRequestDto);

        return new MsgResponseDto("댓글 수정 성공", HttpStatus.OK.value());
    }

    public MsgResponseDto deleteComment(Long postId, Long commentId, User user) {
        checkPost(postId);
        Comment comment =findByCommentIdAndUser(commentId,user);

        commentRepository.delete(comment);

        return new MsgResponseDto("댓글 삭제 성공", HttpStatus.OK.value());
    }

    private Post checkPost(Long postId) {
        return postRepository.findById(postId).orElseThrow (
                () -> new CustomException(NOT_FOUND_BOARD)
        );
    }


    // 사용자의 권한 확인 - 게시글
    private Comment findByCommentIdAndUser(Long comment_id, User user) {
        Comment comment;

        // ADMIN
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            comment = commentRepository.findById(comment_id).orElseThrow(
                    () -> new CustomException(NOT_FOUND_COMMENT)
            );
            // USER
        } else {
            comment = commentRepository.findByIdAndUserId(comment_id, user.getId()).orElseThrow (
                    () -> new CustomException(NOT_FOUND_COMMENT_OR_AUTHORIZATION)
            );
        }

        return comment;
    }
}
