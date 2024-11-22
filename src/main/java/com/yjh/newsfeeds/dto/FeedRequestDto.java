package com.yjh.newsfeeds.dto;

import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final String content;
    private final String userId;

    public FeedRequestDto(String content, String userId){
        this.content = content;
        this.userId = userId;
    }

}
