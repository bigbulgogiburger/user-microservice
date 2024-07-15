package com.example.usermicroservice.service;

import com.example.usermicroservice.dto.UserDto;
import com.example.usermicroservice.vo.RequestUser;

public interface UserService {
    UserDto createUser(UserDto user);
}
