package com.blogapp.service.impl;

import com.blogapp.dto.RegistrationDto;
import com.blogapp.dto.UserDto;
import com.blogapp.exception.AuthenticationException;
import com.blogapp.exception.DataNotFoundException;
import com.blogapp.mapper.AuthorMapper;
import com.blogapp.mapper.UserMapper;
import com.blogapp.model.Author;
import com.blogapp.model.Role;
import com.blogapp.model.User;
import com.blogapp.repository.AuthorRepository;
import com.blogapp.repository.RoleRepository;
import com.blogapp.repository.UserRepository;
import com.blogapp.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final AuthorRepository authorRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username).orElseThrow(() -> new
                DataNotFoundException(String.format("Пользователь '%s' не найден", username)));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                user.getRoles().stream()
                        .map(role -> new SimpleGrantedAuthority(role.getName()))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User createNewUser(RegistrationDto registrationUserDto) {
        String password = passwordEncoder.encode(registrationUserDto.getPassword());
        List<Role> roles = List.of(roleRepository.findByName("ROLE_USER"));
        User user = UserMapper.mapToUser(registrationUserDto, password, roles);
        return userRepository.save(user);
    }

    @Override
    public Author createNewAuthor(RegistrationDto dto) {
        Role role = roleRepository.findByName("ROLE_AUTHOR");
        Optional<User> user = userRepository.findByUsername(dto.getUsername());
        Author author;
        if (user.isEmpty()) {
            throw new DataNotFoundException("Пользователь с " + dto.getUsername() + " именем не найден в базе данных");
        } else {
            User user1 = user.get();
            user1.getRoles().add(role);
            userRepository.save(user1);
            author = AuthorMapper.mapToAuthorFromUser(user1);
            authorRepository.save(author);
        }
        return author;
    }

    @Override
    public List<UserDto> findAllUsers(Pageable page) {
        return userRepository.findAll(page)
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new
                DataNotFoundException(String.format("Пользователь по id '%d' не найден", userId)));
        return UserMapper.mapToUserDto(user);
    }

    @Override
    public UserDto updateUser(Long userId, String currentUsername, RegistrationDto dto) {
        User user = checkUserId(userId);
        if (!user.getUsername().equals(currentUsername)) {
            throw new AuthenticationException("Неверное имя пользователя");
        }
        String password = passwordEncoder.encode(dto.getPassword());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(password);
        return UserMapper.mapToUserDto(userRepository.save(user));
    }

    private User checkUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new DataNotFoundException("Пользователь по id " +
                userId + " не найден в базе данных"));
    }
}
