package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Feed;
import lombok.Getter;

@Getter
public class FeedResponseDto {
    private final Long id;
    private final Long userId;
    private final String email;
    private final String content;
    private final String nickName;

    public FeedResponseDto(Long id, Long userId, String content, String nickName, String email) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.nickName = nickName;
        this.email = email;

    }


    public static FeedResponseDto toDto(Feed feed){
        return new FeedResponseDto(feed.getId(), feed.getUserId(), feed.getContent(), feed.getNickName(), feed.getEmail());
    }


}
