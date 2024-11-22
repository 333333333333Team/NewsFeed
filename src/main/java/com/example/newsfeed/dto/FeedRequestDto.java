package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final String email;
    private final String nickname;
    private final String content;
    private final String phone;

    public FeedRequestDto(String email, String nickname, String content, String phone){

        this.email =email;
        this.nickname =nickname;
        this.content = content;
        this.phone = phone;
    }

}
