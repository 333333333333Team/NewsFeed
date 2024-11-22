package com.example.newsfeed.entitiy;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_id", nullable = false)
    private User target;

    @Setter
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private RequestStatus requestStatus;

    public Friend(User user, User target, RequestStatus requestStatus) {
        this.user = user;
        this.target = target;
        this.requestStatus = requestStatus;
    }

}
