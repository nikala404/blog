package com.example.blog.service;

import com.example.blog.client.ReqResClient;
import com.example.blog.dto.ReqResUser;
import com.example.blog.dto.UserResponse;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService  {

    private final  UserRepository userRepository;
    private final ReqResClient reqResClient;



    public UserService(UserRepository userRepository, ReqResClient reqResClient) {
        this.userRepository = userRepository;
        this.reqResClient = reqResClient;
    }


   public void registerUser(User user){
            userRepository.save(user);
   }

    public void fetchAndStoreUsers() {
        int page = 1;
        UserResponse response;
        do {
            response = reqResClient.getAllUsers(page);
            if (response != null && !response.getData().isEmpty()) {
                List<User> users = response.getData().stream()
                        .map(this::mapToUser)
                        .toList();
                userRepository.saveAll(users);
                page++;
            }
        } while (response != null && !response.getData().isEmpty());
    }

    private User mapToUser(ReqResUser reqResUser) {
        User user = new User();
        user.setFirstName(reqResUser.getFirst_name());
        user.setLastName(reqResUser.getLast_name());
        user.setUserName(reqResUser.getEmail());
        return user;
    }

}
