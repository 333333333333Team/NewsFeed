package com.example.newsfeed.repository;

import com.example.newsfeed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Feedrepository extends JpaRepository<Feed, Long> {
}
