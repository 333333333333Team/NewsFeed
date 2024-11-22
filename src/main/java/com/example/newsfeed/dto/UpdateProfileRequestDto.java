package com.example.newsfeed.dto;


import lombok.Getter;

@Getter
public class UpdateProfileRequestDto {

    private final String nickName;

    private final String phone;

    private final String password;

    private final String newPassword;

    public UpdateProfileRequestDto(String nickName, String phone, String password, String newPassword) {
        this.nickName = nickName;
        this.phone = phone;
        this.password = password;
        this.newPassword = newPassword;
    }
}
