package com.blogapp.service;

import com.blogapp.dto.RegistrationDto;
import com.blogapp.dto.UserDto;
import com.blogapp.model.Author;
import com.blogapp.model.User;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);

    User createNewUser(RegistrationDto registrationUserDto);

    List<UserDto> findAllUsers(Pageable page);

    UserDto findById(long userId);

    UserDto updateUser(Long userId, String currentUsername, RegistrationDto dto);

    Author createNewAuthor(RegistrationDto dto);

}
