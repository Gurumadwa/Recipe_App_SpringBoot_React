package com.comments.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import com.comments.dto.UserResponse;

@FeignClient(name = "USER-SERVICE", url = "http://localhost:3434/")
public interface UserClient {
	
	@GetMapping("/users/get-user/{userId}")
    public UserResponse findUserByUserId(@PathVariable Long userId, @RequestHeader("Authorization") String token);  

}
