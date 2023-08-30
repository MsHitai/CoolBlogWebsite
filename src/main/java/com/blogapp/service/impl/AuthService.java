package com.blogapp.service.impl;


import com.blogapp.dto.JwtRequest;
import com.blogapp.dto.JwtResponse;
import com.blogapp.dto.RegistrationDto;
import com.blogapp.dto.UserDto;
import com.blogapp.exception.AuthenticationException;
import com.blogapp.model.Author;
import com.blogapp.model.User;
import com.blogapp.utility.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDetailsImpl userService;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<Object> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(),
                    authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Неправильный логин или пароль");
        }
        UserDetails userDetails = userService.loadUserByUsername(authRequest.getUsername());
        String token = jwtTokenUtils.generateToken(userDetails);
        return ResponseEntity.ok(new JwtResponse(token));
    }

    public ResponseEntity<Object> createNewUser(@RequestBody RegistrationDto registrationUserDto) {
        checkPassword(registrationUserDto);
        if (userService.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            throw new AuthenticationException("Пользователь с указанным именем уже существует");
        }
        User user = userService.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(user.getId(), user.getUsername(), user.getEmail()));
    }

    public ResponseEntity<Object> createNewAuthor(RegistrationDto dto) {
        checkPassword(dto);
        if (userService.findByUsername(dto.getUsername()).isEmpty()) {
            throw new AuthenticationException("Пользователь с указанным именем не существует");
        }
        Author author = userService.createNewAuthor(dto);
        return ResponseEntity.ok(new UserDto(author.getId(), author.getName(), author.getEmail()));
    }

    private void checkPassword(RegistrationDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            throw new AuthenticationException("Пароли не совпадают");
        }
    }
}
