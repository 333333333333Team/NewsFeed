package com.example.newsfeed.repository;


import com.example.newsfeed.entitiy.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendRepository extends JpaRepository<Friend, Long> {
    List<Friend> findByUserUserId(Long userId);
    Optional<Friend> findByUserUserIdAndTargetUserId(Long userId, Long targetUserId);
    void deleteByUserUserIdAndTargetUserId(Long userId, Long targetUserId);

}
