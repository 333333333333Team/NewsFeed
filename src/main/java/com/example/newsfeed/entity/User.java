package com.example.newsfeed.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Setter
@Getter
@Entity
@Table(name = "user")
@EnableJpaAuditing
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userid;

    @Column(nullable = false, length = 45)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 45)
    private String nickName;

    @Column(nullable = false, length = 45)
    private String phone;

    @Column(nullable = false)
    private boolean resign;

    public User(){

    }


    public User(String email, String password, String nickName, String phone) {
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.phone = phone;
    }

    public void updateProfile(String nickName, String phone) {
        this.nickName = nickName;
        this.phone = phone;
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
}
