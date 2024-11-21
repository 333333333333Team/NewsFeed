package com.example.newsfeed.dto;

import com.example.newsfeed.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UserRequestDto {

    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "이메일 형식이 올바르지 않습니다.")
    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    private final String email;

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8}", message = "비밀번호는 8자이상 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private final String password;

    private final String nickname;
    private final String phone;
    private final boolean resign;


    public UserRequestDto(String email, String password, String nickname, String phone, boolean resign) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.resign = resign;
    }

    public User toEntity(){
        return new User(this.email, this.password, this.nickname, this.phone, this.resign);
    }

}
