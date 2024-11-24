package com.example.newsfeed.repository;
import com.example.newsfeed.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByUserId(Long userId);
    List<Feed> findByUserIdIn(Collection<Long> userIds);
}
