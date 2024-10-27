package com.example.blog.service;

import com.example.blog.model.User;
import com.example.blog.model.UserPrincipal;
import com.example.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private final UserRepository userRepository;


    private UserPrincipal userPrincipal;

    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserName( username );
        if(user == null){
            throw  new UsernameNotFoundException( "User Not Found" );
        }
        return new UserPrincipal( user );

    }
}
