package com.example.newsfeed.repository;

import com.example.newsfeed.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Repository
public interface ProfileRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long userid);
}
