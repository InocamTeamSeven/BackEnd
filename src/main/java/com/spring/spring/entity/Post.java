package com.spring.spring.entity;

import com.spring.spring.dto.PostRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "post")
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "contents", nullable = false)
    private String contents;

    @Column(name = "image")
    private String image;

    @Column(name = "username", nullable = false)
    private String username;


    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE)
    private List<PostLike> postLikeList = new ArrayList<>();

    // 게시글 작성
    public Post(PostRequestDto postRequestDto, String image) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
        this.image = image;
    }

    // 게시글 수정
    public void update(PostRequestDto postRequestDto) {
        this.title = postRequestDto.getTitle();
        this.contents = postRequestDto.getContents();
//        this.image = postRequestDto.getImage();
    }
}