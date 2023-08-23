package com.blogapp.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "webBlog", groupId = "blog")
    void listener(String data) {
        System.out.println("Listener received data " + data);
    }
}
