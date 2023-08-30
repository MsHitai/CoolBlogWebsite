package com.blogapp.service.impl;

import com.blogapp.dto.PostDto;
import com.blogapp.exception.DataNotFoundException;
import com.blogapp.mapper.PostMapper;
import com.blogapp.model.Post;
import com.blogapp.repository.PostRepository;
import com.blogapp.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final PostRepository postRepository;
    private AtomicInteger viewCount = new AtomicInteger(0);

    @Override
    public PostDto viewPost(Long postId) {
        Post post = checkPostId(postId);
        kafkaTemplate.send("posts-views", String.valueOf(postId));
        post.setViewCount(viewCount);
        postRepository.save(post);
        return PostMapper.mapToPostDto(post);
    }

    private Post checkPostId(Long postId) {
        return postRepository.findById(postId).orElseThrow(() -> new DataNotFoundException("Пост по id " +
                postId + " не найден в базе данных"));
    }

    @KafkaListener(topics = "posts-views", groupId = "posts-views-Id")
    void viewListener(String data) {
        viewCount.incrementAndGet();
    }
}
