package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final String content;

    public FeedRequestDto(String content){

        this.content = content;


    }

}
