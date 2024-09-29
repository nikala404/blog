package com.example.blog.service;

import com.example.blog.model.Post;
import com.example.blog.model.User;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService  {

    private final  UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

   public void registerUser(User user){
            userRepository.save(user);
   }

//   public User updateUser(User user){
//
//   }
}
