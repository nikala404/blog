package com.example.blog.mapper;

import com.example.blog.dto.ReqResUser;
import com.example.blog.model.User;

public class UserMapper {

    public static User toEntity(ReqResUser apiUser) {
        User userEntity = new User();
        userEntity.setFirstName(apiUser.getFirst_name());
        userEntity.setLastName(apiUser.getLast_name());
        userEntity.setUserName(apiUser.getEmail());
        return userEntity;
    }
}