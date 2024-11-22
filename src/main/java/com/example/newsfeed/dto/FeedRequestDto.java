package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final String email;
    private final String nickname;
    private final String content;


    public FeedRequestDto(String email, String content, String nickname){

        this.email =email;
        this.content = content;
        this.nickname =nickname;

    }

}
