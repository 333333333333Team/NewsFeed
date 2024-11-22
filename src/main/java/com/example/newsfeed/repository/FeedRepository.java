package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed>findByEmail(String email);
}
