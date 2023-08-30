package com.blogapp.controller;

import com.blogapp.dto.JwtRequest;
import com.blogapp.dto.RegistrationDto;
import com.blogapp.service.impl.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public AuthController(AuthService authService, KafkaTemplate<String, String> kafkaTemplate) {
        this.authService = authService;
        this.kafkaTemplate = kafkaTemplate;
    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthToken(@Valid @RequestBody JwtRequest authRequest) {
        log.info("Поступил запрос на получения токена от {}", authRequest.getUsername());
        kafkaTemplate.send("webBlog", "Поступил запрос на получения токена от {}", authRequest.getUsername());
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@Valid @RequestBody RegistrationDto registrationUserDto) {
        log.info("Поступил запрос на регистрацию пользователя {}", registrationUserDto.getUsername());
        kafkaTemplate.send("webBlog", "Поступил запрос на регистрацию пользователя " +
                registrationUserDto.getUsername());
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("/registration/author")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createNewAuthor(@Valid @RequestBody RegistrationDto dto) {
        log.info("Поступил запрос на регистрацию пользователя как автора {}", dto.getUsername());
        kafkaTemplate.send("webBlog", "Поступил запрос на регистрацию пользователя как автора " +
                dto.getUsername());
        return authService.createNewAuthor(dto);
    }
}
