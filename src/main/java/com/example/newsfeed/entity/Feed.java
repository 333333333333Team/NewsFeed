package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.math.BigInteger;

@Getter
@Entity
@Table(name = "feeds")
public class Feed extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column(columnDefinition = "longtext")
    private String content;

    //한명의 회원은 여러개의 게시글을 작성 할 수 있다.
   // @ManyToOne
   // @JoinColumn(name = "user_id")
   // private Member member;


    public Feed(){}

    public Feed(String content){
        this.content = content;
    }

//  public void setMember(Member member){
//      this.member = member;
//  }

}
