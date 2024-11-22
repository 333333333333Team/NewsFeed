package com.example.newsfeed.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FriendResponseDto {
    private Long friendId;
    private Long userId;
    private Long targetId;
    private String requestStatus;
}
