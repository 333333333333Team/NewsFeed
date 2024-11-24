package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Feed;
import lombok.Getter;

@Getter
public class FeedResponseDto {
    private final Long id;
    private final String email;
    private final String content;
    private final String nickName;
    private final Long userId;

    public FeedResponseDto(Long id, String content, String nickName, String email, Long userId) {
        this.id = id;
        this.content = content;
        this.nickName = nickName;
        this.email = email;
        this.userId = userId;
    }


    public static FeedResponseDto toDto(Feed feed){
        return new FeedResponseDto(feed.getId(), feed.getContent(), feed.getNickName(), feed.getEmail(), feed.getUserId());
    }


}
