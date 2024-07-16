package com.example.usermicroservice.service;

import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.entity.UserEntity;
import com.example.usermicroservice.vo.RequestUser;

import java.util.List;

public interface UserService {
    List<UserEntity> getUserByAll();
    UserDto getUserById(String id);
    UserDto createUser(UserDto user);
}
