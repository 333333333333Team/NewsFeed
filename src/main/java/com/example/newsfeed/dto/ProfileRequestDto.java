package com.example.newsfeed.dto;

import lombok.Getter;

@Getter
public class ProfileRequestDto {
    private final String email;

    private final String password;

    private final String passwordCheck;

    private final String nickName;

    private final String phone;

    public ProfileRequestDto(String email, String password, String passwordCheck, String nickName, String phone) {
        this.email = email;
        this.password = password;
        this.passwordCheck = passwordCheck;
        this.nickName = nickName;
        this.phone = phone;
    }


}
