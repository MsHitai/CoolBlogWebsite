package com.blogapp;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class BlogApp {

    public static void main(String[] args) {
        SpringApplication.run(BlogApp.class, args);
    }

    @Bean //temporary
    CommandLineRunner commandLineRunner(KafkaTemplate<String, String> kafkaTemplate) {
        return args -> kafkaTemplate.send("webBlog", "welcome to the blog");
    }

}
