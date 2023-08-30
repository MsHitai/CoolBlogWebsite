package com.blogapp.controller;

import com.blogapp.dto.PostDto;
import com.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/{postId}")
    public PostDto viewPost(@PathVariable Long postId) {
        log.info("Пост по id {} просмотрен", postId);
        return postService.viewPost(postId);
    }
}
