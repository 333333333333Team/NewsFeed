package com.newsfeeds.dto;

import com.newsfeeds.entity.Feed;
import lombok.Getter;

import java.math.BigInteger;

@Getter
public class FeedResponseDto {
    private final BigInteger id;
    private final String content;
    private final String nickname;

    public FeedResponseDto(BigInteger id, String content, String nickname) {
        this.id = id;
        this.content = content;
        this.nickname = nickname;
    }


    public static FeedResponseDto toDto(Feed feed){
        return new FeedResponseDto(feed.getId(), feed.getContent());
    }


}
