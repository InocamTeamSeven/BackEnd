package com.spring.spring.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "comments")
@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "content", nullable = false)
    private String comment;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;


    @ManyToOne
    @JoinColumn(name = "posts_id")
    private Post post;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;


    public Comment(String comment, String username, String password) {
        this.comment = comment;
        this.username = username;
        this.password = password;
    }

}