package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class ProfileResponseDto {

    private final Long userid;

    private final String email;

    private final String nickName;

    private final String phone;

    public ProfileResponseDto(Long userid, String email, String nickName, String phone) {
        this.userid = userid;
        this.email = email;
        this.nickName = nickName;
        this.phone = phone;
    }
}
