package com.example.newsfeed.dto;

import com.example.newsfeed.entity.Feed;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class FeedResponseDto {
    private final Long id;
    private final String email;
    private final String content;
    private final String nickname;

    public FeedResponseDto(Long id, String content, String nickname, String email) {
        this.id = id;
        this.content = content;
        this.nickname = nickname;
        this.email = email;
    }


    public static FeedResponseDto toDto(Feed feed){
        return new FeedResponseDto(feed.getUserId(), feed.getContent(), feed.getNickname(), feed.getEmail());
    }


}
