package com.example.newsfeed.repository;

import com.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findNickNameAndEmailAndPhoneByUserId(Long userId);
}
