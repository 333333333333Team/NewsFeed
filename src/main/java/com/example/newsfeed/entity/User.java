package com.example.newsfeed.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class User  extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private String email;
    private String password;
    private String nickname;
    private String phone;
    private boolean resign;

    public User() {}

    public User(String email, String password, String nickname, String phone, boolean resign) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.resign = resign;
    }


}
