package com.newsfeeds.repository;

import com.newsfeeds.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Feedrepository extends JpaRepository<Feed, Long> {
}
