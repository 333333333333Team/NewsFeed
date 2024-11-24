package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
@Setter
//@Table(name = "feeds")
public class Feed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String email;
    private String nickName;
    private String phone;
    private String content;

    public Feed(){}

    public Feed( Long userId,String email, String nickName,String phone, String content){

        this.userId=userId;
        this.content = content;
        this.email = email;
        this.nickName = nickName;
        this.phone = phone;
    }


}
