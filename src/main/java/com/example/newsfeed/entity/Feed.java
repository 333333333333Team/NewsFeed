package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;

@Getter
@Entity
@Setter
//@Table(name = "feeds")
public class Feed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String nickname;
    private String phone;
    private String content;


    //한명의 회원은 여러개의 게시글을 작성 할 수 있다.
   // @ManyToOne
   // @JoinColumn(name = "user_id")
   // private Member member;


    public Feed(){}

    public Feed(String email, String nickname,String phone, String content){

        this.content = content;
        this.email = email;
        this.nickname = nickname;
        this.phone = phone;
    }

//  public void setMember(Member member){
//      this.member = member;
//  }


    public void update(String content){
        this.content = content;

    }

}
