package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class FeedRequestDto {

    private final String email;
    private final String nickName;
    private final String content;
    private final String phone;
    private final Long userId;

    public FeedRequestDto(String email, String nickName, String content, String phone, Long userId){

        this.email =email;
        this.nickName =nickName;
        this.content = content;
        this.phone = phone;
        this.userId = userId;
    }

}
