package com.blogapp.controller;

import com.blogapp.dto.PostDto;
import com.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping("/{postId}")
    public PostDto viewPost(@PathVariable Long postId) {
        log.info("Пост по id {} просмотрен", postId);
        return postService.viewPost(postId);
    }

    @PostMapping()
    @Secured("ROLE_AUTHOR")
    public PostDto addPost(@RequestHeader("X-Blog-User-Id") Long authorId,
                           @RequestParam("header") String header,
                           @RequestBody() String text) throws IOException {
        log.info("Получен запрос POST на добавление поста с заголовком {}, текстом {}", header, text);
        return postService.addPost(text, header, authorId);
    }
}
