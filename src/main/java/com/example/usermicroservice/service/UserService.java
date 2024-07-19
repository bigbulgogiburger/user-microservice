package com.example.usermicroservice.service;

import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<UserEntity> getUserByAll();
    UserDto getUserById(String id);
    UserDto createUser(UserDto user);

    UserDto getUserDetailsByEmail(String userName);
}
