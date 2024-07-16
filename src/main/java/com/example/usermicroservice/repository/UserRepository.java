package com.example.usermicroservice.repository;

import com.example.usermicroservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUserId(String id);
}
