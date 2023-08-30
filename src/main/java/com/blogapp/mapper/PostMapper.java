package com.blogapp.mapper;


import com.blogapp.dto.PostDto;
import com.blogapp.model.Author;
import com.blogapp.model.Post;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PostMapper {

    public Post mapToPost(PostDto postDto, Author author) {
        return Post.builder()
                .id(postDto.getId())
                .image(postDto.getImage())
                .text(postDto.getText())
                .author(author)
                .created(postDto.getCreated())
                .viewCount(postDto.getViewCount())
                .build();
    }

    public PostDto mapToPostDto(Post post) {
        return PostDto.builder()
                .id(post.getId())
                .text(post.getText())
                .image(post.getImage())
                .created(post.getCreated())
                .authorId(post.getAuthor().getId())
                .viewCount(post.getViewCount())
                .build();
    }
}
