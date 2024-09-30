package com.example.blog.client;
import com.example.blog.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        value = "reqResClient",
        url = "https://reqres.in/api"
)
public interface ReqResClient {

    @GetMapping("/users")
    UserResponse getAllUsers(@RequestParam("page") int page);

}
