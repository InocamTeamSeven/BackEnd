package com.spring.spring.repository;

import com.spring.spring.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    boolean existsByPostIdAndUserId(Long post_id, Long userId);
    void deleteByPostIdAndUserId(Long post_id, Long userId);
}