package com.example.newsfeed.dto;


import lombok.Getter;

@Getter
public class UpdateProfileRequestDto {

    private final String nickName;

    private final String phone;

    private final String oldPassword;

    private final String newPassword;

    public UpdateProfileRequestDto(String nickName, String phone, String oldPassword, String newPassword) {
        this.nickName = nickName;
        this.phone = phone;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }
}
