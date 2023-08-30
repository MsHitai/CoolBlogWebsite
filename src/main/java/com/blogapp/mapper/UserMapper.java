package com.blogapp.mapper;


import com.blogapp.dto.RegistrationDto;
import com.blogapp.dto.UserDto;
import com.blogapp.model.Role;
import com.blogapp.model.User;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class UserMapper {

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .build();
    }

    public User mapToUser(RegistrationDto registrationUserDto, String password, List<Role> roles) {
        return User.builder()
                .username(registrationUserDto.getUsername())
                .email(registrationUserDto.getEmail())
                .password(password)
                .roles(roles)
                .build();
    }

}
