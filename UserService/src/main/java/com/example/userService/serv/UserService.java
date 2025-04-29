package com.example.userService.serv;

import com.example.userService.dto.CreateUserRequest;
import com.example.userService.dto.LoginRequest;
import com.example.userService.dto.LoginResponse;
import com.example.userService.dto.UserResponse;

public interface UserService {
	
	UserResponse register(CreateUserRequest req);
	  LoginResponse login(LoginRequest req);
	  UserResponse getById(String id);

}
