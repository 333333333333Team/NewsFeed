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


    public FeedResponseDto(Long id,String email,String content, String nickname) {
        this.id = id;
        this.email = email;
        this.content = content;
        this.nickname = nickname;
    }


    public static FeedResponseDto toDto(Feed feed){
        return new FeedResponseDto(feed.getId(), feed.getEmail(), feed.getContent(), feed.getNickname());
    }


}
