package com.yjh.newsfeeds.repository;

import com.yjh.newsfeeds.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Feedrepository extends JpaRepository<Feed, Long> {
}
