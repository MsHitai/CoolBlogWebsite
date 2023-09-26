package com.blogapp.service.impl;

import com.blogapp.dto.PostDto;
import com.blogapp.exception.DataNotFoundException;
import com.blogapp.mapper.PostMapper;
import com.blogapp.model.Author;
import com.blogapp.model.Post;
import com.blogapp.repository.AuthorRepository;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PostRepository postRepository;
    private final AuthorRepository authorRepository;

    @Override
    public PostDto viewPost(Long postId) {
        Post post = checkPostId(postId);
        kafkaTemplate.send("posts-views", String.valueOf(postId));
        return PostMapper.mapToPostDto(post);
    }

    @Override
    public PostDto addPost(String text, String header, Long authorId) {
        Post post = new Post();
        Author author = checkAuthorId(authorId);
        post.setAuthor(author);
        post.setHeader(header);
        post.setText(text);
        post.setCreated(LocalDateTime.now());
        return PostMapper.mapToPostDto(postRepository.save(post));
    }

    private Post checkPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Пост по id " +
                postId + " не найден в базе данных"));
    }

    private Author checkAuthorId(Long authorId) {
        return authorRepository.findById(authorId).orElseThrow(() -> new DataNotFoundException("Автора по id " +
                authorId + " не найден в базе данных"));
    }

    @KafkaListener(topics = "posts-views", groupId = "posts-views-Id")
    void viewListener(String postId) {
        Post post = checkPostId(Long.parseLong(postId));
        post.getViewCount().incrementAndGet();
        postRepository.save(post);
    }
}
