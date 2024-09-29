package com.example.blog.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        value = "reqresclient",
        url = "https://reqres.in/api"
)
public interface ReqresClient {
    @GetMapping("/users")
    ResponseEntity<Object> getAllUsers();

}
