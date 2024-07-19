package com.example.usermicroservice.repository;

import com.example.usermicroservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    UserEntity findByUserId(String id);

    Optional<UserEntity> findByEmail(String username);
}
