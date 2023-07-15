package com.spring.spring.service;

import com.spring.spring.dto.CommentRequestDto;
import com.spring.spring.dto.CommentResponseDto;
import com.spring.spring.entity.Comment;
import com.spring.spring.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }



    public CommentResponseDto createComment(CommentRequestDto requestDto) {
        Comment comment = new Comment(requestDto.getComment(), requestDto.getUsername(), requestDto.getPassword());
        LocalDateTime now = LocalDateTime.now();
        comment.setCreatedAt(now);
        Comment savedComment = commentRepository.save(comment);
        return new CommentResponseDto(savedComment.getId(),savedComment.getComment(), savedComment.getUsername(), savedComment.getCreatedAt(), savedComment.getModifiedAt());
    }


    public List<CommentResponseDto> getComments() {
        List<Comment> comments = commentRepository.findAll();
        List<CommentResponseDto> commentResponseDtos = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponseDto responseDto = new CommentResponseDto(
                    comment.getId(),
                    comment.getComment(),
                    comment.getUsername(),
                    comment.getCreatedAt(),
                    comment.getModifiedAt()
            );
            commentResponseDtos.add(responseDto);
        }

        return commentResponseDtos;
    }


    public CommentResponseDto updateComment(Long id, CommentRequestDto requestDto, String username, String password) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if (!comment.getUsername().equals(username) || !comment.getPassword().equals(password)) {
            throw new IllegalArgumentException("작성자 또는 비밀번호가 일치하지 않습니다.");
        }
        comment.setComment(requestDto.getComment());
        comment.setModifiedAt(LocalDateTime.now());
        Comment updatedComment = commentRepository.save(comment);
        return new CommentResponseDto(updatedComment.getId(), updatedComment.getComment(), updatedComment.getUsername(), updatedComment.getCreatedAt(), updatedComment.getModifiedAt());
    }



    public void deleteComment(Long id, String username, String password) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if (!comment.getUsername().equals(username) || !comment.getPassword().equals(password)) {
            throw new IllegalArgumentException("작성자 또는 비밀번호가 일치하지 않습니다.");
        }
        commentRepository.delete(comment);
    }
}