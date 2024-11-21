package com.yjh.newsfeeds.dto;

import lombok.Getter;

@Getter
public class FeedsRequestDto {

    private final String content;
    private final String userId;

    public FeedsRequestDto(String content, String userId){
        this.content = content;
        this.userId = userId;
    }

}
