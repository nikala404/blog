package com.example.blog.controller;

import com.example.blog.model.User;
import com.example.blog.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody User user){
        userService.registerUser(user);
        return ResponseEntity.ok("User registered successfully");
    }

    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable){
        Page<User> users = userService.getAllUsers(pageable);
        return ResponseEntity.ok(users);
    }
}
