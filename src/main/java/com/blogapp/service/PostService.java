package com.blogapp.service;

import com.blogapp.dto.PostDto;

import java.io.IOException;

public interface PostService {
    PostDto viewPost(Long postId);

    PostDto addPost(String description, String header, Long userId) throws IOException;

}
