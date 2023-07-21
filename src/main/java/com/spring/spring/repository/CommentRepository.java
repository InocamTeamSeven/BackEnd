package com.spring.spring.repository;

import com.spring.spring.entity.Comment;
import com.spring.spring.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long comment_id, Long id);
}
