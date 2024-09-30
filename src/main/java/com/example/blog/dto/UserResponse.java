package com.example.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponse {
    private int page;
    private List<ReqResUser> data;
}
