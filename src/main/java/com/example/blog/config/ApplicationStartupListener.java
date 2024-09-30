package com.example.blog.config;

import com.example.blog.service.UserService;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartupListener {

    private final UserService userService;

    public ApplicationStartupListener(UserService userService) {
        this.userService = userService;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void onApplicationEvent() {
        userService.fetchAndStoreUsers();
        System.out.println("Users fetched from ReqRes and saved in the database.");
    }
}
