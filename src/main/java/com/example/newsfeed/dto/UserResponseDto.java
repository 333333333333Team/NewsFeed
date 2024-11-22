package com.example.newsfeed.dto;

import com.example.newsfeed.entity.User;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {
   private final Long userId;
   private final String email;
   private final String password;
   private final String nickname;
   private final String phone;
   private final LocalDateTime createdDate;
   private final LocalDateTime modifiedDate;
   private final boolean resign;

   public UserResponseDto(Long userId,String password, String email, String nickname, String phone, LocalDateTime createdDate, LocalDateTime modifiedDate, boolean resign) {
       this.userId = userId;
       this.password = password;
       this.email = email;
       this.nickname = nickname;
       this.phone = phone;
       this.createdDate = createdDate;
       this.modifiedDate = modifiedDate;
       this.resign = resign;
   }
    public static UserResponseDto toProfileDto(User user){
       return new UserResponseDto(
               null,
               null,
               user.getNickName(),
               user.getEmail(),
               user.getPhone(),
               null,
               null,
               false
       );
    }


    public static UserResponseDto toDto(User user) {
       return new UserResponseDto(
               user.getUserId(),
               user.getPassword(),
               user.getEmail(),
               user.getNickName(),
               user.getPhone(),
               user.getCreatedDate(),
               user.getModifiedDate(),
               user.isResign()
       );
   }


}
