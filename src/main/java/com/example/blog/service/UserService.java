package com.example.blog.service;


import com.example.blog.BlogApplication;
import com.example.blog.model.User;
import com.example.blog.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final AuthenticationManager authManager;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, AuthenticationManager authManager, JWTService jwtService) {
        this.userRepository = userRepository;
        this.authManager = authManager;
        this.jwtService = jwtService;
    }

    public void registerUser(User user) {

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }


    public String verify(User user) {

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUserName());
        }
        return "User is not authenticated";
    }

    public String deleteUserByUserName(String targetUserName, HttpSession session, Principal principal) {
        Optional<User> user = userRepository.findUserByUserName(targetUserName);

        if (user.isEmpty()) {
            System.out.println("User does not exist: " + targetUserName);
            return "User Does not Exist";
        }

        User targetUser = user.get();
        User authenticatedUser = userRepository.findUserByUserName(principal.getName()).get();

        if (targetUser.getUserName().equals(authenticatedUser.getUserName()) || authenticatedUser.getRoles().contains("ADMIN")) {
            userRepository.delete(targetUser);
            if (targetUser.getUserName().equals(authenticatedUser.getUserName())) {
                session.invalidate();
            }
            return "User Deleted Successfully";

        } else {
            System.out.println("User " + principal.getName() + " cannot delete " + targetUserName);
            return "You Can't Delete Other User! Reason:"+'[' + principal.getName() + " : " + targetUserName + ']';
        }
    }


    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }
}
