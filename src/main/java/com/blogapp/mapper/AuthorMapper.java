package com.blogapp.mapper;

import com.blogapp.dto.AuthorDto;
import com.blogapp.model.Author;
import com.blogapp.model.User;
import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorMapper {

    public AuthorDto mapToAuthorDto(Author author) {
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .email(author.getEmail())
                .userId(author.getUser().getId())
                .build();
    }

    public Author mapToAuthor(AuthorDto authorDto, User user) {
        return Author.builder()
                .id(authorDto.getId())
                .name(authorDto.getName())
                .email(authorDto.getEmail())
                .user(user)
                .build();
    }

    public Author mapToAuthorFromUser(User user) {
        return Author.builder()
                .name(user.getUsername())
                .email(user.getEmail())
                .user(user)
                .build();
    }
}
